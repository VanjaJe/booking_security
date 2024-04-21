import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Certificate} from "../model/model.module";

@Component({
  selector: 'app-revoke-certificate-dialog',
  templateUrl: './revoke-certificate-dialog.component.html',
  styleUrls: ['./revoke-certificate-dialog.component.css']
})
export class RevokeCertificateDialogComponent {
  reason : string = ''

  // constructor(@Inject(MAT_DIALOG_DATA) public certificate: Certificate) {}
  constructor(private dialogRef: MatDialogRef<RevokeCertificateDialogComponent>) {}

  close(): void {
    this.dialogRef.close(this.reason);
  }
}
