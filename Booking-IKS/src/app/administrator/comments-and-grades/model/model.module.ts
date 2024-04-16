import { Accommodation } from "src/app/accommodations/accommodation/model/model.module";
import { Status, User, Host } from "src/app/account/model/model.module";

export interface CommentAndGrade{
  id?: number;
  text: string;
  date: Date;
  rating: number;
  status: Status;
  guest: Guest;
  host?:Host;
  accommodation?:Accommodation;
}

export interface Guest extends User {
  favoriteAccommodations?:Accommodation[];
  cancellations?:number;
}
