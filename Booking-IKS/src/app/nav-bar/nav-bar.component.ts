import {Component} from '@angular/core';
import {NotificationService} from "../notification/notification.service";
import {Notification} from "../notification/notification/model/model.module";
import {UserService} from "../account/account.service";
import {Router} from "@angular/router";
import {ReservationRequest} from "../accommodations/accommodation/model/model.module";
import {CertificateService} from "../certificates/certificate.service";
import {
  CertificateRequest,
  CertificateRequestStatus,
  CertificateType,
  KeyUsages
} from "../certificates/model/model.module";
import {User} from "../account/model/model.module";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {

  notifications: Notification[] = []
  role: string = '';
  user:User;
  constructor(private service: NotificationService, private auth: UserService,
              private router: Router,private certificateService:CertificateService) {}

  ngOnInit(): void {
    // this.service.getAll().subscribe({
    //   next: (data: Notification[]) => {
    //     this.notifications = data
    //   },
    //   error: (_) => {console.log("Greska!")}
    // });
    this.auth.userState.subscribe((result) => {
      this.role = result;
    });
  }

  logout() {
    localStorage.removeItem('user');
    this.router.navigate(['logIn']);
    this.auth.setUser();

  }

  public loadNotifications() {
    this.service.getAll(this.auth.getUserId()).subscribe({
      next: (data: Notification[]) => {
        this.notifications = data
      },
      error: (_) => {console.log("Greska!")}
    });
  }

  generateCertificate() {
    this.auth.getUser(this.auth.getUserId()).subscribe(
      (data) => {
        this.user=data;
        if(this.user.account?.username!=undefined){
          const subjectData={
            email: this.user.account.username,
            name:this.user.firstName,
            lastname: this.user.lastName,
            id:this.user.id
          }
          const certificateRequest: CertificateRequest = {
            subject: subjectData,
            date: new Date(),
            requestStatus: CertificateRequestStatus.ACTIVE,
            certificateType: CertificateType.END_ENTITY,
            keyUsages: [KeyUsages.DIGITAL_SIGNATURE]
          };
          console.log(certificateRequest)

          this.certificateService.createCertificateRequest(certificateRequest).subscribe(
            {
              next: (data: ReservationRequest) => {
              },
              error: (_: any) => {
              }
            });
        }
      },
      (error) => {
        console.error('Error fetching user:', error);
      });


  }
}
