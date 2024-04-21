import {Component} from '@angular/core';
import {
  CertificateRequest,
  CertificateRequestStatus,
  CertificateTemplate,
  CertificateType,
  KeyUsages,
  SubjectData
} from "../model/model.module";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ReservationRequest} from "../../accommodations/accommodation/model/model.module";
import {UserService} from "../../account/account.service";
import {CertificateService} from "../certificate.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-certificate',
  templateUrl: './create-certificate.component.html',
  styleUrls: ['./create-certificate.component.css']
})
export class CreateCertificateComponent {

  certificateForm: FormGroup;
  user:SubjectData;
  // @ts-ignore
  certificateTypeOptions: string[] = []
  selectedKeyUsages: string[]=[];
  selectedTemplates: string[]=[];

  constructor(private fb: FormBuilder, private router: Router,
              private userService:UserService,
              private certificateService:CertificateService) {}

  ngOnInit() {
    this.certificateTypeOptions=Object.values(CertificateType).map(item => String(item));
    this.selectedKeyUsages=Object.values(KeyUsages).map(item => String(item));
    this.selectedTemplates=Object.values(CertificateTemplate).map(item => String(item));

    this.certificateForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      id: ['', [Validators.required]],
      date: ['', Validators.required],
      keyUsages: ['', Validators.required],
      templates: ['']
    });
  }
  onSubmit() {
    if (this.certificateForm.invalid) {
      return;
    }
    if(this.certificateForm.valid){
      this.user={
        email: this.certificateForm.get('email')?.value,
        name:this.certificateForm.get('name')?.value,
        lastname: this.certificateForm.get('surname')?.value,
        id:this.certificateForm.get('id')?.value
      }
      var type:CertificateType;
      const ct:CertificateTemplate[] = this.certificateForm.get('templates')?.value;

      if (ct.includes(CertificateTemplate.CERTIFICATE_AUTHORITY)) {
        type = CertificateType.INTERMEDIATE;
      }
      else {
        type = CertificateType.END_ENTITY;
      }

      const certificateRequest: CertificateRequest = {
        subject: this.user,
        date: this.certificateForm.get('date')?.value,
        requestStatus: CertificateRequestStatus.ACTIVE,
        certificateType: type,
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
    }
  }

  onChange() {
    console.log("METODA")
    // @ts-ignore
    const selectedCertificateType = this.certificateForm.get('templates').value;
    switch (selectedCertificateType) {
      case CertificateTemplate.Code_Signing_Certificate:
        // @ts-ignore
        this.certificateForm.get('keyUsages').setValue([KeyUsages.DIGITAL_SIGNATURE]);
        break;

      case CertificateTemplate.Email_Signing_Certificate:
        // @ts-ignore
        this.certificateForm.get('keyUsages').setValue([KeyUsages.DIGITAL_SIGNATURE, KeyUsages.NON_REPUDIATION]);
        break;

      case CertificateTemplate.SSL_TLS_Client:
        // @ts-ignore
        this.certificateForm.get('keyUsages').setValue([KeyUsages.DIGITAL_SIGNATURE, KeyUsages.KEY_ENCIPHERMENT]);
        break;

      case CertificateTemplate.SSL_TLS_Server:
        // @ts-ignore
        this.certificateForm.get('keyUsages').setValue([KeyUsages.DIGITAL_SIGNATURE, KeyUsages.KEY_ENCIPHERMENT]);
        break;

      case CertificateTemplate.CERTIFICATE_AUTHORITY:
        // @ts-ignore
        this.certificateForm.get('keyUsages').setValue([KeyUsages.DIGITAL_SIGNATURE, KeyUsages.KEY_ENCIPHERMENT,
                                                        KeyUsages.CERTIFICATE_SIGNING, KeyUsages.CRL_SIGNING,
                                                        KeyUsages.DATA_ENCIPHERMENT, KeyUsages.NON_REPUDIATION,
                                                        KeyUsages.ENCRYPT_ONLY, KeyUsages.KEY_AGREEMENT]);
        break;

      default:
        // @ts-ignore
        this.certificateForm.get('keyUsages').setValue([]);
        break;
    }
  }
}
