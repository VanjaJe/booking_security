import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationCardComponent } from './notification-card/notification-card.component';
import {MatChipsModule} from "@angular/material/chips";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";
import {MaterialModule} from "../infrastructure/material/material.module";
import {RouterLink} from "@angular/router";
import { NotificationComponent } from './notification/notification.component';



@NgModule({
  declarations: [
    NotificationCardComponent,
    NotificationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterLink
  ],
  exports: [
    NotificationCardComponent
  ]
})
export class NotificationModule { }
