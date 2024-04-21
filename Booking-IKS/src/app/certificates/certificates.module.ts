import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewCertificatesComponent } from './view-certificates/view-certificates.component';
import { CertificateCardComponent } from './certificate-card/certificate-card.component';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { CertificateRequestsComponent } from './certificate-requests/certificate-requests.component';
import { RequestCardComponent } from './request-card/request-card.component';
import {MaterialModule} from "../infrastructure/material/material.module";
import {RouterLink} from "@angular/router";
import { RevokeCertificateDialogComponent } from './revoke-certificate-dialog/revoke-certificate-dialog.component';



@NgModule({
  declarations: [
    ViewCertificatesComponent,
    CertificateCardComponent,
    CreateCertificateComponent,
    CertificateRequestsComponent,
    RequestCardComponent,
    RevokeCertificateDialogComponent
  ],
  imports: [
    CommonModule,
    RouterLink,
    MaterialModule
  ]
})
export class CertificatesModule { }
