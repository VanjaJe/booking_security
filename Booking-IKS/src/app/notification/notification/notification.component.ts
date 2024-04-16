import {Component, OnInit} from '@angular/core';
import {Accommodation} from "../../accommodations/accommodation/model/model.module";
import {AccommodationsService} from "../../accommodations/accommodations.service";
import {NotificationService} from "../notification.service";
import {UserService} from "../../account/account.service";
import {GuestNotificationSettings, HostNotificationSettings} from "./model/model.module";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit{

  role:string = "";
  userId:number;
  hostSettings:HostNotificationSettings;
  guestSettings:GuestNotificationSettings;
  isReservationCancelled=false;
  isReservationResponded = false;
  isReservationCreated=false;
  isRating=false;
  isAccommodationRating=true;

  constructor(private  userService: UserService, private notificationService: NotificationService) {
  }

  ngOnInit(): void {

    this.role = this.userService.getRole();
    this.userId = this.userService.getUserId()

    if (this.role == "ROLE_HOST") {
      this.notificationService.getHostSettings(this.userId).subscribe(
          (data) => {
            this.hostSettings = data;
            console.log(this.hostSettings)
            console.log(this.hostSettings.requestCreated)
            this.isAccommodationRating = this.hostSettings.accommodationRated;
            this.isRating = this.hostSettings.rated;
            this.isReservationCreated = this.hostSettings.requestCreated;
            this.isReservationCancelled = this.hostSettings.reservationCancelled;
            // this.settingsForm.value.cancelledReservation = this.hostSettings.eservationCancelled;
            // this.settingsForm.value.creatingRequest = this.hostSettings.isRequestCreated;
            // this.settingsForm.value.ratingHost = this.hostSettings.isRated;
          },
          (error) => {
            console.error('Error fetching guest:', error);
          });
    }
    else {
      this.notificationService.getGuestSettings(this.userId).subscribe(
          (data) => {
            this.guestSettings = data;
            this.isReservationResponded = this.guestSettings.requestResponded;
          },
          (error) => {
            console.error('Error fetching guest:', error);
          });
    }
  }

  setAll(completed: boolean, type:string ) {
    if (type=="isAccommodationRating") {
      this.hostSettings.accommodationRated = completed;
      console.log(this.hostSettings.accommodationRated)
    }
    if (type=="isRating") {
      this.hostSettings.rated = completed;
    }
    if (type=="isReservationCreated") {
      this.hostSettings.requestCreated = completed;
    }
    if (type=="isReservationCancelled") {
      this.hostSettings.reservationCancelled = completed;
    }
    if (type=="isReservationResponded") {
      this.guestSettings.requestResponded = completed;
      console.log(this.guestSettings.requestResponded)
    }
  }

  public saveSettings() {
    if (this.role == "ROLE_HOST") {
      this.notificationService.updateHostSettings(this.userId, this.hostSettings).subscribe(
          (data) => {
          },
          (error) => {
            console.error('Error fetching guest:', error);
          });
    }

    else {
      this.notificationService.updateGuestSettings(this.userId, this.guestSettings).subscribe(
          (data) => {

          },
          (error) => {
            console.error('Error fetching guest:', error);
          });
    }
  }
}

