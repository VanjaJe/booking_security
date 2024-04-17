import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationComponent } from './tabsView/reservation.component';
import {MaterialModule} from "../infrastructure/material/material.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RouterLink} from "@angular/router";
import {LayoutModule} from "../layout/layout.module";
import { ReservationsViewComponent } from './reservations-view/reservations-view.component';
import { RequestsViewComponent } from './requests-view/requests-view.component';
import { FavoritesViewComponent } from './favorites-view/favorites-view.component';
import {AccommodationsModule} from "../accommodations/accommodations.module";


@NgModule({
  declarations: [
    ReservationComponent,
    ReservationsViewComponent,
    RequestsViewComponent,
    FavoritesViewComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    BrowserAnimationsModule,
    RouterLink,
    LayoutModule,
    AccommodationsModule
  ]
})
export class ReservationsModule { }
