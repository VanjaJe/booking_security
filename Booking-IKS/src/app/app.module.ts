import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import {LayoutModule} from "./layout/layout.module";
import {AccommodationDetailsComponent} from "./accommodations/accommodation-details/accommodation-details.component";
import {AccommodationsModule} from "./accommodations/accommodations.module";
import { AccountModule } from './account/account.module';
import { RegistrationComponent } from './account/registration/registration.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { AdministratorModule } from './administrator/administrator.module';
import {NotificationModule} from "./notification/notification.module";
import {ReservationsModule} from "./reservations/reservations.module";
import {InterceptService} from "./interceptors/intercept.service";
import {ToastrModule} from "ngx-toastr";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CommentsModule } from './comments/comments.module';
import {MapModule} from "./map/map.module";
import { AccountActivationComponent } from './account-activation/account-activation.component';
import {MatButtonModule} from "@angular/material/button";
import {ReportsModule} from "./reports/reports.module";
import {NgChartsModule } from 'ng2-charts';
import {MaterialModule} from "./infrastructure/material/material.module";


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    AccountActivationComponent,
  ],
  imports: [
    NgChartsModule,
    ReportsModule,
    MaterialModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    ReactiveFormsModule,
    AccommodationsModule,
    CommentsModule,
    MapModule,
    NotificationModule,
    ReservationsModule,
    AccountModule,
    HttpClientModule,
    AdministratorModule,
    ToastrModule.forRoot(),
    // MatButtonModule,
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: InterceptService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
