import {Component, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {
  RequestStatus,
  ReservationRequest
} from "../../accommodations/accommodation/model/model.module";
import {ReservationsService} from "../reservations.service";
import {FormControl, FormGroup} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {UserService} from "../../account/account.service";
import {Host, User} from "src/app/account/model/model.module";
import {MatSnackBar} from "@angular/material/snack-bar";
import {of} from "rxjs";
import {
  GuestNotificationSettings,
  HostNotificationSettings,
  Notification,
  NotificationType
} from "../../notification/notification/model/model.module";
import {SocketService} from "../../socket/socket.service";
import {NotificationService} from "../../notification/notification.service";
import {Guest} from "../../administrator/comments-and-grades/model/model.module";


@Component({
  selector: 'app-requests-view',
  templateUrl: './requests-view.component.html',
  styleUrls: ['./requests-view.component.css']
})
export class RequestsViewComponent implements OnInit {
  requests: ReservationRequest[] = [];
  dataSource = new MatTableDataSource<ReservationRequest>([]); // Initialize with an empty array
  displayedColumns: string[] = [];
  numberOfCancellations: number;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  guest: User;
  host: Host;
  settings:GuestNotificationSettings;
  startDate: string = "";
  endDate: string = "";
  userId: number;
  role: string;
  statusOptions: string[] = [];
  filterRequestsForm: FormGroup = new FormGroup({
    accommodationName: new FormControl(),
    requestStatus: new FormControl()
  });

  constructor(private requestService: ReservationsService, private userService: UserService,
              private snackBar: MatSnackBar, private socketService: SocketService,
              private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.role = this.userService.getRole();
    this.userId = this.userService.getUserId();
    if (this.role == "ROLE_HOST") {
      const hostId = this.userService.getUserId();
      this.userService.getUser(hostId).subscribe(
        (data) => {
          this.host = data;
          console.log(this.host)
        },
        (error) => {
          console.error('Error fetching guest:', error);
        });

      this.displayedColumns = ['timeSlot', 'price', 'guest', 'report', 'cancellations', 'accommodation', 'status', 'accept', 'deny']
    } else {
      this.displayedColumns = ['timeSlot', 'price', 'host', 'accommodation', 'status', 'delete']
    }

    // this.getSettings();
    this.statusOptions = Object.values(RequestStatus).map(item => String(item));
    this.fetchData();
  }

  filterClicked() {
    this.fetchData();
  }

  fetchData() {
    const selectedName = this.filterRequestsForm.value.accommodationName;
    const selectedStatus = <RequestStatus>this.filterRequestsForm.value.requestStatus;
    if (this.role == "ROLE_GUEST") {
      this.requestService.getAllForGuest(this.userId, selectedStatus, selectedName, this.startDate, this.endDate).subscribe({
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

    } else if (this.role == "ROLE_HOST") {
      const selectedName = this.filterRequestsForm.value.accommodationName;
      const selectedStatus = <RequestStatus>this.filterRequestsForm.value.requestStatus;
      this.requestService.getAllForHost(this.userId, selectedStatus, selectedName, this.startDate, this.endDate).subscribe({
        next: (data: ReservationRequest[]) => {
          this.requests = data;
          this.getCancellationsForGuest();
          this.dataSource = new MatTableDataSource<ReservationRequest>(this.requests);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        },
        error: (_) => {
          console.log("Error fetching data from ReservationsService");
        }
      });
    }
  }

  getFormatedDate(date: Date, format: string) {
    const datePipe = new DatePipe('en-US');
    return datePipe.transform(date, format);
  }

  dateRangeChange(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement) {
    if(dateRangeEnd.value!="" && dateRangeStart.value!=""){
      // @ts-ignore
      this.startDate = this.getFormatedDate(new Date(dateRangeStart.value), "yyyy-MM-dd");
      // @ts-ignore
      this.endDate = this.getFormatedDate(new Date(dateRangeEnd.value), "yyyy-MM-dd");
    }else{
      this.startDate = "";
      this.endDate = "";
    }

  }


  public reportGuest(guestId: number) {
    this.userService.getUser(guestId).subscribe(
      (data) => {
        this.guest = data;

        this.userService.reportGuest(this.host.id, this.guest).subscribe(
          (data) => {
            this.snackBar.open("Guest is reported!", 'Close', {
              duration: 3000,
            });
          },
          (error) => {
            if (error.status===404) {
              this.snackBar.open("Guest is reported already!", 'Close', {
                duration: 3000,
              });
            }
            else {
              this.snackBar.open("You can't report guest!", 'Close', {
                duration: 3000,
              });
            }
          });
      },
      (error) => {
        console.error('Error fetching guest:', error);
      });
  }

  deleteRequest(request: ReservationRequest) {
    //the guest deletes the request if request status is pending
    if (request.status == RequestStatus.PENDING) {
      this.requestService.delete(request.id).subscribe(
        {
          next: (data: ReservationRequest) => {
            this.snackBar.open("Request deleted!", 'Close', {
              duration: 3000,
            });
            this.fetchData();
          },
          error: (_) => {
            this.snackBar.open("Request can't be deleted!", 'Close', {
              duration: 3000,
            });
          }
        });
    }
  }

  denyRequest(request: ReservationRequest) {
    // @ts-ignore
    this.getSettings(request.guest?.id);
    if (request.status == RequestStatus.PENDING) {
      this.requestService.deny(request).subscribe(
        {
          next: (data: ReservationRequest) => {

            const text:string="Host denied your request!";

            if (this.checkNotificationStatus(NotificationType.RESERVATION_RESPONSE)) {
              this.createNotification(request.guest as Guest, text, NotificationType.RESERVATION_RESPONSE);
              // @ts-ignore
              this.socketService.sendMessageUsingSocket(text,request.accommodation?.host.id, request.guest.id);
            }

            this.snackBar.open("Request denied!", 'Close', {
              duration: 3000,
            });
            this.fetchData();
          },
          error: (_) => {
            this.snackBar.open("Request can't be denied!", 'Close', {
              duration: 3000,
            });
          }
        });
    }
  }

  acceptRequest(request: ReservationRequest) {
    // @ts-ignore
    this.getSettings(request.guest?.id);
    if (request.status == RequestStatus.PENDING) {
      this.requestService.accept(request).subscribe(
        {
          next: (data: ReservationRequest) => {
            const text:string="Host accepted your request!";

            if (this.checkNotificationStatus(NotificationType.RESERVATION_RESPONSE)) {
              this.createNotification(request.guest as Guest, text, NotificationType.RESERVATION_RESPONSE);
              // @ts-ignore
              this.socketService.sendMessageUsingSocket(text,request.accommodation?.host.id, request.guest.id);
            }
            this.snackBar.open("Request accepted!", 'Close', {
              duration: 3000,
            });
            this.fetchData();
          },
          error: (_) => {
            this.snackBar.open("Request can't be accepted!", 'Close', {
              duration: 3000,
            });
          }
        });
    }
  }

  private getCancellationsForGuest() {
    for (const request of this.requests) {
      // @ts-ignore
      this.requestService.getCancellations(request.guest.id).subscribe({
        next: (data: number) => {
          // @ts-ignore
          request.guest.cancellations = data;
        },
        error: (_) => {
          console.log("Error fetching cancellation count for a guest from ReservationsService");
        }
      });
    }
  }

  private createNotification(guest:Guest, text:string, notificationType:NotificationType) {

    const notification: Notification = {
      text: text,
      date: this.formatDate(new Date()),
      type: notificationType,
      user: guest
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

  public getSettings(guestId: number)  {
    this.notificationService.getGuestSettings(guestId).subscribe(
        {
          next: (data: GuestNotificationSettings) => {
            this.settings = data;
            console.log("SETINGS")
            console.log(this.settings)
          },
          error: () => {
          }
        });
  }

  public checkNotificationStatus(type:NotificationType ):boolean{
    if (NotificationType.RESERVATION_RESPONSE==type && this.settings.requestResponded) {
      console.log("USAOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
      return true;
    }
    return false;
  }
}
