import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Certificate} from "../model/model.module";

@Component({
  selector: 'app-certificate-dialog',
  templateUrl: './certificate-dialog.component.html',
  styleUrls: ['./certificate-dialog.component.css']
})
export class CertificateDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public certificate: Certificate) { }

}
