import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import { ToastrService } from 'ngx-toastr';
import {UserService} from "../account.service";
import {SharedService} from "../../shared/shared.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SocketService} from "../../socket/socket.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  // form: FormGroup;


  constructor(
    private fb: FormBuilder,
    private authenticationService: UserService,
    private router: Router,
    private toastr: ToastrService,
    // private sharedService: SharedService
    private _snackBar: MatSnackBar,
    private socketService: SocketService
  ) {}


  form = this.fb.group({
    username : [null, [Validators.required]],
    password: [null, [Validators.required]]
  });



  ngOnInit() {
  }

  submit() {
    const auth: any = {};
    auth.username = this.form.value.username;
    auth.password = this.form.value.password;

    this.authenticationService.login(auth).subscribe(
      result => {
        console.log("VANJAAAAAAAAAAAAAAAAAAAAAAAAA")
        this.toastr.success('Successful login!');
        localStorage.setItem('user', JSON.stringify(result));
        this.authenticationService.setUser()
        this.socketService.initializeWebSocketConnection(this.authenticationService.getUserId());
        this.router.navigate(['home']);
      },
      error => {
        this.openSnackBar("Your account is not active. Check your email address!")
        // this.toastr.error(error.error);
      }
    );
  }

  openSnackBar(message:string) {
    this._snackBar.open(message, "close",{
      duration:2000
    });
  }
}
