import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationComponent } from './registration.component';
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MaterialModule} from "../../infrastructure/material/material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrService} from "ngx-toastr";
import {UserService} from "../account.service";
import {Router} from "@angular/router";
import { RouterTestingModule } from '@angular/router/testing';
import {By} from "@angular/platform-browser";

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegistrationComponent],
      providers: [MatSnackBar, UserService],
      imports: [HttpClientTestingModule,MaterialModule,BrowserAnimationsModule, RouterTestingModule ]
    });
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('all invalid fields', () => {
    component.registrationForm.controls['username'].setValue('');
    component.registrationForm.controls['password'].setValue('');
    component.registrationForm.controls['confirmPassword'].setValue('');
    component.registrationForm.controls['firstName'].setValue('');
    component.registrationForm.controls['lastName'].setValue('');
    component.registrationForm.controls['address'].setValue('');
    component.registrationForm.controls['country'].setValue('');
    component.registrationForm.controls['city'].setValue('');
    component.registrationForm.controls['role'].setValue('');
    component.registrationForm.controls['phoneNumber'].setValue('');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('username empty field', () => {
    component.registrationForm.controls['username'].setValue('');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('password and confirmPassword empty fields', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('');
    component.registrationForm.controls['confirmPassword'].setValue('');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('firstName empty field', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('lastName empty field', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('address empty field', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('country empty field', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vaja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('city empty field', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vaja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('role empty field', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vaja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('phoneNumber empty field', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vaja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('password is not valid', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('all valid fields', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('Vanja_987');
    component.registrationForm.controls['confirmPassword'].setValue('Vanja_987');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeFalsy();
  });

  it('username is not email', () => {
    component.registrationForm.controls['username'].setValue('vanja');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('vanja');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

  it('password and confirmPassword not the same', () => {
    component.registrationForm.controls['username'].setValue('vanja@example.com');
    component.registrationForm.controls['password'].setValue('vanja');
    component.registrationForm.controls['confirmPassword'].setValue('jole');
    component.registrationForm.controls['firstName'].setValue('vanja');
    component.registrationForm.controls['lastName'].setValue('vanja');
    component.registrationForm.controls['address'].setValue('vanja');
    component.registrationForm.controls['country'].setValue('vanja');
    component.registrationForm.controls['city'].setValue('vanja');
    component.registrationForm.controls['role'].setValue('ROLE_HOST');
    component.registrationForm.controls['phoneNumber'].setValue('123');

    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css("#registerBtn"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });
});
