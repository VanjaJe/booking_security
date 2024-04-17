import {Component, Input} from '@angular/core';
import {Notification} from "../notification/model/model.module";
import {MatChipOption} from "@angular/material/chips";

@Component({
  selector: 'app-notification-card',
  templateUrl: './notification-card.component.html',
  styleUrls: ['./notification-card.component.css']
})
export class NotificationCardComponent {
  @Input()
  notification : Notification | undefined;

}
