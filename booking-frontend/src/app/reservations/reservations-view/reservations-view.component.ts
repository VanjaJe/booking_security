import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {RequestStatus, ReservationRequest} from "../../accommodations/accommodation/model/model.module";
import {ReservationsService} from "../reservations.service";
import { MatSnackBar } from '@angular/material/snack-bar';
import {
  HostNotificationSettings,
  Notification,
  NotificationType
} from "../../notification/notification/model/model.module";
import {User} from "../../account/model/model.module";
import {NotificationService} from "../../notification/notification.service";
import { Host } from "src/app/account/model/model.module";
import {SocketService} from "../../socket/socket.service";

@Component({
  selector: 'app-reservations-view',
  templateUrl: './reservations-view.component.html',
  styleUrls: ['./reservations-view.component.css']
})
export class ReservationsViewComponent implements OnInit{
  requests: ReservationRequest[] = [];
  dataSource = new MatTableDataSource<ReservationRequest>([]); // Initialize with an empty array
  displayedColumns: string[] = ['timeSlot','price', 'accommodation','status','cancel'];
  settings: HostNotificationSettings;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private service: ReservationsService, private snackBar: MatSnackBar, private notificationService: NotificationService,
              private socketService: SocketService) { }

  ngOnInit(): void {
   this.fetchData();
  }

  fetchData(){
    this.service.getAll(RequestStatus.ACCEPTED,"").subscribe({
      next: (data: ReservationRequest[]) => {
        this.requests = data;
        this.dataSource = new MatTableDataSource<ReservationRequest>(this.requests);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (_) => {
        console.log("Error fetching data from ReservationsService");
      }
    });
  }

  cancelReservation(reservation: ReservationRequest) {
    // @ts-ignore
    this.getSettings(reservation.accommodation?.host.id)
    if (this.todayIsBeforeDeadline(reservation)) {
      this.service.cancel(reservation).subscribe(
        {
          next: (data: ReservationRequest) => {

            const text="User " + reservation.guest?.account?.username + " has cancelled reservation.";

            if (this.checkNotificationStatus(NotificationType.RESERVATION_CANCELLED)) {
              console.log("KREIRAOOOOOOOOOOO")

              this.createNotification(reservation.accommodation?.host as Host, text, NotificationType.RESERVATION_CANCELLED);
              // @ts-ignore
              this.socketService.sendMessageUsingSocket(text,reservation.guest.id,reservation.accommodation.host.id);
            }

            this.snackBar.open("Reservation canceled!", 'Close', {
              duration: 3000,
            });
            this.fetchData();

          },
          error: (_) => {
            this.snackBar.open("Reservation can't be canceled!", 'Close', {
              duration: 3000,
            });
          }
        });
    }
    else{
      this.snackBar.open("The deadline for canceling the reservation has expired.", 'Close', {
        duration: 3000,
      });
    }
  }

  todayIsBeforeDeadline(reservation: ReservationRequest): boolean {
    const daysBeforeDeadline = reservation.accommodation?.reservationDeadline;
    const reservationStartDate = reservation.timeSlot?.startDate;

    if (reservationStartDate && daysBeforeDeadline !== undefined) {
      const resultDate = new Date(reservationStartDate);
      resultDate.setDate(resultDate.getDate() - daysBeforeDeadline); // Subtract days from the date
      const today = new Date();

      return today < resultDate;
    }

    return false;
  }


  private createNotification(host: Host, text:string, notificationType:NotificationType) {

    const notification: Notification = {
      text: text,
      date: this.formatDate(new Date()),
      type: notificationType,
      user: host
    };
    this.notificationService.createNotification(notification).subscribe(
        {
          next: (data: Notification) => {
          },
          error: () => {
          }
        }
    );
  }
  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  }

  public getSettings(hostId:number)  {
    this.notificationService.getHostSettings(hostId).subscribe(
        {
          next: (data: HostNotificationSettings) => {
            this.settings = data;
            console.log("SETINGS")
            console.log(this.settings)
          },
          error: () => {
          }
        });
  }

  public checkNotificationStatus(type:NotificationType ):boolean{
    if (NotificationType.RESERVATION_REQUEST==type && this.settings.requestCreated) {
      return true;
    }
    if (NotificationType.ACCOMMODATION_RATED==type && this.settings.accommodationRated) {
      return true;
    }
    if (NotificationType.HOST_RATED==type && this.settings.rated) {
      return true;
    }
    if (NotificationType.RESERVATION_CANCELLED==type && this.settings.reservationCancelled) {
      return true;
    }
    return false;
  }

}


