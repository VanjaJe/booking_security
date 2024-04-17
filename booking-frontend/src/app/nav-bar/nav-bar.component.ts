import {ChangeDetectorRef, Component} from '@angular/core';
import {NotificationService} from "../notification/notification.service";
import {Notification} from "../notification/notification/model/model.module";
import {UserService} from "../account/account.service";
import {Router} from "@angular/router";
import {audit} from "rxjs";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {

  notifications: Notification[] = []
  role: string = '';
  constructor(private service: NotificationService, private auth: UserService,
              private router: Router) {}

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
}
