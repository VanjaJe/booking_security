import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ViewAccommodationsComponent} from "./accommodations/view-accommodations/view-accommodations.component";
import {HeaderComponent} from "./layout/header/header.component";
import {AccommodationDetailsComponent} from "./accommodations/accommodation-details/accommodation-details.component";
import {HomeComponent} from "./layout/home/home.component";
import { LoginComponent } from './account/login/login.component';
import { RegistrationComponent } from './account/registration/registration.component';
import { AccountManagementComponent } from './account/account-management/account-management.component';
import { CommentsAndGradesCardsComponent } from './administrator/comments-and-grades-cards/comments-and-grades-cards.component';
import { ReportedUserCardsComponent } from './administrator/reported-user-cards/reported-user-cards.component';
import { AccommodationApprovalCardsComponent } from './administrator/accommodation-approval-cards/accommodation-approval-cards.component';
import {CreateAccommodationComponent} from "./accommodations/create-accommodation/create-accommodation.component";
import {EditAccommodationsDatesComponent} from "./accommodations/edit-accommodations-dates/edit-accommodations-dates.component";
import {AccommodationComponent} from "./accommodations/accommodation/accommodation.component";
import {ReservationComponent} from "./reservations/tabsView/reservation.component";
import {AccountActivationComponent} from "./account-activation/account-activation.component";
import {AccommodationUpdateComponent} from "./accommodations/accommodation-update/accommodation-update.component";
import {ReportComponent} from "./reports/report/report.component";
import {NotificationComponent} from "./notification/notification/notification.component";
import {ViewCertificatesComponent} from "./certificates/view-certificates/view-certificates.component";
import {CreateCertificateComponent} from "./certificates/create-certificate/create-certificate.component";
import {CertificateRequestsComponent} from "./certificates/certificate-requests/certificate-requests.component";
import {GuardService} from "./certificates/guard.service";


const routes: Routes = [
  {component: AccommodationDetailsComponent, path:"home/accommodations/accommodationDetails/:id",canActivate:[GuardService]},
  {component: AccommodationDetailsComponent, path:"accommodationApproval/accommodationDetails/:id",canActivate:[GuardService]},
  {component: AccommodationComponent, path:"accommodation"},
  {component: ViewAccommodationsComponent, path:"home/accommodations",canActivate:[GuardService]},
  {component: CreateAccommodationComponent, path:"create",canActivate:[GuardService]},
  {component: EditAccommodationsDatesComponent, path:"home/accommodations/accommodationDetails/:id/editDates",canActivate:[GuardService]},
  {component: AccommodationDetailsComponent, path:"reservations/accommodationDetails/:id",canActivate:[GuardService]},
  {component: ReservationComponent, path:"reservations",canActivate:[GuardService]},
  {component: ViewCertificatesComponent, path:"certificatesView",canActivate:[GuardService]},
  {component: CreateCertificateComponent, path:"createCertificate",canActivate:[GuardService]},
  {component: CertificateRequestsComponent, path:"certificateRequestsView",canActivate:[GuardService]},
  {component: LoginComponent, path:"logIn",canActivate:[GuardService]},
  {component: AccountActivationComponent, path:"account-activation/:accessToken/:username",canActivate:[GuardService]},
  {component: RegistrationComponent, path:"signIn",canActivate:[GuardService]},
  {component: AccountManagementComponent, path:"myAccount",canActivate:[GuardService]},
  {component: CommentsAndGradesCardsComponent, path:'commentsAndRatings',canActivate:[GuardService]},
  {component: NotificationComponent, path:"notificationSettings",canActivate:[GuardService]},
  {component: CommentsAndGradesCardsComponent, path:'home/accommodations/accommodationDetails/:id/commentsAndRatings',canActivate:[GuardService]},
  {component: ReportedUserCardsComponent, path:'reportedUsers',canActivate:[GuardService]},
  {component: AccommodationApprovalCardsComponent, path:'accommodationApproval',canActivate:[GuardService]},
  {component: AccommodationUpdateComponent, path:'home/accommodations/accommodationUpdate/:id',canActivate:[GuardService]},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path:"home",component:HomeComponent},
  {component: ReportComponent, path:'reports',canActivate:[GuardService]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
