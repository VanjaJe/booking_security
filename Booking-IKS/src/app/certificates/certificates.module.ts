import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewCertificatesComponent } from './view-certificates/view-certificates.component';
import { CreateCertificateComponent } from './create-certificate/create-certificate.component';
import { CertificateRequestsComponent } from './certificate-requests/certificate-requests.component';
import {MaterialModule} from "../infrastructure/material/material.module";
import {RouterLink} from "@angular/router";
import { RevokeCertificateDialogComponent } from './revoke-certificate-dialog/revoke-certificate-dialog.component';
import { CertificateDialogComponent } from './certificate-dialog/certificate-dialog.component';
import {AccommodationsModule} from "../accommodations/accommodations.module";



@NgModule({
  declarations: [
    ViewCertificatesComponent,
    CreateCertificateComponent,
    CertificateRequestsComponent,
    RevokeCertificateDialogComponent,
    CertificateDialogComponent,
  ],
    imports: [
        CommonModule,
        RouterLink,
        MaterialModule,
        AccommodationsModule
    ]
})
export class CertificatesModule { }
