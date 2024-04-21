import { Component } from '@angular/core';
import {CertificateRequest, CertificateRequestStatus, CertificateType, KeyUsages} from "../model/model.module";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccommodationType, ReservationRequest} from "../../accommodations/accommodation/model/model.module";
import {UserService} from "../../account/account.service";
import {CertificateService} from "../certificate.service";
import {User} from "../../account/model/model.module";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-certificate',
  templateUrl: './create-certificate.component.html',
  styleUrls: ['./create-certificate.component.css']
})
export class CreateCertificateComponent {

  certificateForm: FormGroup;
  user:User;
  // @ts-ignore
  certificateTypeOptions: string[] = []
  selectedKeyUsages: string[]=[];
  constructor(private fb: FormBuilder, private router: Router,
              private userService:UserService,
              private certificateService:CertificateService) {}

  ngOnInit() {
    this.certificateTypeOptions=Object.values(CertificateType).map(item => String(item));
    this.selectedKeyUsages=Object.values(KeyUsages).map(item => String(item));
    this.certificateForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      date: ['', Validators.required],
      certificateType: ['', Validators.required],
      keyUsages: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.certificateForm.invalid) {
      return;
    }
    const formData = this.certificateForm.value as CertificateRequest;
    this.userService.getUserByUsername( this.certificateForm.get('email')?.value).subscribe(
      (data) => {
        this.user=data;
        const certificateRequest: CertificateRequest = {
          subject: this.user,
          date: this.certificateForm.get('date')?.value,
          requestStatus: CertificateRequestStatus.ACTIVE,
          certificateType: this.certificateForm.get('certificateType')?.value,
          keyUsages:this.certificateForm.get('keyUsages')?.value
        };
        console.log(certificateRequest);
        this.certificateService.createCertificateRequest(certificateRequest).subscribe(
          {
            next: (data: ReservationRequest) => {
              this.router.navigate(['/certificateRequestsView']);
            },
            error: (_: any) => {
            }
          });
      },
      (error) => {
        console.error('Error fetching user:', error);
      });
  }

}
