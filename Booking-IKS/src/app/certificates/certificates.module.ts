import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewCertificatesComponent } from './view-certificates/view-certificates.component';
import { CertificateCardComponent } from './certificate-card/certificate-card.component';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { CertificateRequestsComponent } from './certificate-requests/certificate-requests.component';
import { RequestCardComponent } from './request-card/request-card.component';
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {MaterialModule} from "../infrastructure/material/material.module";



@NgModule({
  declarations: [
    ViewCertificatesComponent,
    CertificateCardComponent,
    CreateCertificateComponent,
    CertificateRequestsComponent,
    RequestCardComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class CertificatesModule { }
