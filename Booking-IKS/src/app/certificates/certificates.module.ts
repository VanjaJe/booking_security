import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CertificateViewComponent } from './certificate-requests/certificate-view.component';
import { ViewCertificatesComponent } from './view-certificates/view-certificates.component';
import { CertificateCardComponent } from './certificate-card/certificate-card.component';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { CertificateRequestsComponent } from './certificate-requests/certificate-requests.component';
import { RequestCardComponent } from './request-card/request-card.component';



@NgModule({
  declarations: [
    CertificateViewComponent,
    ViewCertificatesComponent,
    CertificateCardComponent,
    CreateCertificateComponent,
    CertificateRequestsComponent,
    RequestCardComponent
  ],
  imports: [
    CommonModule
  ]
})
export class CertificatesModule { }
