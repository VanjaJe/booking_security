import {User} from "../../../account/model/model.module";
import {Timestamp} from "rxjs";

export interface Notification {
  id?: number;
  text: string;
  date: string;
  type: NotificationType;
  user?:User;
}

export interface NotificationSettings {
  id?: number;
  user: User;
}

export interface  GuestNotificationSettings extends NotificationSettings{
  requestResponded:boolean;
}

export interface HostNotificationSettings extends  NotificationSettings{
  requestCreated: boolean;
  reservationCancelled: boolean;
  rated: boolean;
  accommodationRated: boolean;
}

export enum NotificationType {
  RESERVATION_REQUEST,
  RESERVATION_CANCELLED,
  HOST_RATED,
  ACCOMMODATION_RATED,
  RESERVATION_RESPONSE
}
