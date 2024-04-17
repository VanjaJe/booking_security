import {Account, User} from "../../../account/model/model.module";
import {Guest} from "../../../administrator/comments-and-grades/model/model.module";

export interface Accommodation {
  id?: number;
  name: string;
  description: string;
  address: Address;
  minGuests: number;
  maxGuests: number;
  type: AccommodationType;
  pricePerGuest: boolean;
  automaticConfirmation: boolean;
  status?: AccommodationStatus;
  host: Host;
  reservationDeadline: number;
  amenities?: Amenity[];
  priceList?: PriceListItem[];
  freeTimeSlots?:TimeSlot[];
  price?:number;
  unitPrice?:number;

}
export enum AccommodationStatus {
  ACCEPTED,
  UPDATED,
  CREATED,
  DECLINED
}

export interface Address {
  id?: number;
  country?:string;
  city?:string;
  address?:string;
}

export interface Amenity {
  id?:number;
  name:string;
}

export interface TimeSlot {
  id?:Number;
  startDate: Date;
  endDate: Date;
}

export interface PriceListItem {
  id?: Number;
  timeSlot: TimeSlot;
  price: number;
}
export interface Host extends User{
}

export interface CreateAccommodation {
  id?:number,
  name: string;
  description: string;
  address: Address;
  minGuests: number;
  maxGuests: number;
  type: AccommodationType;
  pricePerGuest: boolean;
  automaticConfirmation: boolean;
  host: Host;
  reservationDeadline: number;
  amenities: Amenity[];
  priceList: PriceListItem[];
  freeTimeSlots: TimeSlot[];
}

export enum AccommodationType {
  HOTEL="HOTEL",
  MOTEL="MOTEL",
  VILLA="VILLA",
  APARTMENT="APARTMENT"
}

export interface EditAccommodation{
  // pricePerGuest: boolean;
  // automaticConfirmation: sboolean;
  // reservationDeadline: number;
  priceList: PriceListItem[];
  // freeTimeSlots: TimeSlot[];
}

export interface FavoriteAccommodations{
  id:number;
  guestId:number;
  accommodationId:number;
}

export interface ReservationRequest {
  id?:number
  timeSlot?: TimeSlot;
  price?: number;
  guest?: Guest;
  accommodation?: Accommodation;
  status?: RequestStatus;
  guestNumber?:number;
}

export enum RequestStatus {
  ACCEPTED="ACCEPTED",
  CANCELLED="CANCELLED",
  PENDING="PENDING",
  DENIED="DENIED"
}

export interface Image {
  url: string,
  file: File
}
