import { Component } from '@angular/core';
import {ReservationsService} from "../reservations.service";
import {UserService} from "../../account/account.service";

@Component({
  selector: 'app-tabsView',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent {
  role:string;

  constructor(private userService:UserService) {
  }

  ngOnInit(): void {
    this.role = this.userService.getRole();
  }

}

