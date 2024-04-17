import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {UserService} from "../account.service";
import {Router} from "@angular/router";
// import {ToastrService} from "ngx-toastr";
import {Account, Address, Role, Status, User} from "../model/model.module";
import {SharedService} from "../../shared/shared.service";

// import { FormControl, Validators } from '@angular/forms';

interface MemberType {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
  })

export class RegistrationComponent implements OnInit {
  selectedValue: string = 'GUEST';
  // newRoles:Role[] = [];

  constructor(
    private fb: FormBuilder,
    private authenticationService: UserService,
    private router: Router,
    // private toastr: ToastrService,
    private sharedService: SharedService
  ) {}

  // passwordMatchValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  //   const password = control.get('password')?.value;
  //   console.log(password);
  //   const confirmPassword = control.get('confirmPassword')?.value;
  //
  //   return password === confirmPassword ? null : { 'passwordMismatch': true };
  // };

  registrationForm = this.fb.group({
    username : ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern(/^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\D*\d).{8,}$/)]],
    confirmPassword: ['', [Validators.required, this.matchValues('password')]],
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    address: ['', [Validators.required]],
    city: ['', [Validators.required]],
    country: ['', [Validators.required]],
    phoneNumber: ['', [Validators.required]],
    role: ['', [Validators.required]]
  });

  matchValues(field: string) {
    return (control: AbstractControl) => {
      const fieldValue = control.value;
      const matchingControl = control.root.get(field);

      if (matchingControl && fieldValue !== matchingControl.value) {
        return { mismatchedValues: true };
      }
      return null;
    };
  }

  ngOnInit(): void {
  }
  onSubmit() {

    if (this.registrationForm.valid) {
      console.log("usao :)")

    const newRoles:Role[] = [];

    const newRole : Role = {
      // @ts-ignore
      name: this.registrationForm.value.role
    }

    newRoles.push(newRole);


    const newAccount : Account = {
      // @ts-ignore
      username: this.registrationForm.value.username,
      // @ts-ignore
      password: this.registrationForm.value.password,
      roles: newRoles,
      status: Status.PENDING
    }

    const newAddress : Address = {
      // @ts-ignore
      address:this.registrationForm.value.address,
      // @ts-ignore
      city:this.registrationForm.value.city,
      // @ts-ignore
      country:this.registrationForm.value.country
    }

    const auth: User = {
      // @ts-ignore
      firstName: this.registrationForm.value.firstName,
      // @ts-ignore
      lastName: this.registrationForm.value.lastName,
      // @ts-ignore
      phoneNumber: this.registrationForm.value.phoneNumber,
      address: newAddress,
      account: newAccount,
      lastPasswordResetDate: new Date()
    }

    this.authenticationService.signup(auth).subscribe(
      result => {
        // this.toastr.success('Successful login!');
        this.router.navigate(['logIn']);

      },
      error => {
        // this.sharedService.openSnack("Your account is not active. Check your email address!")
        // this.toastr.error(error.error);
      }
    );

    // this.authenticationService.sendEmail(auth).subscribe(
    //   result => {
    //     // this.toastr.success('Successful login!');
    //     // localStorage.setItem('user', JSON.stringify(result));
    //     this.router.navigate(['logIn']);
    //   },
    //   error => {
    //     this.toastr.error(error.error);
    //   }
    // );
  }
  }
}
