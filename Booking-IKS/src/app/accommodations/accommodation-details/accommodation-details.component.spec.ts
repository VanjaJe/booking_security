import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { AccommodationDetailsComponent } from './accommodation-details.component';
import {ActivatedRoute, convertToParamMap} from "@angular/router";
import {AccommodationsService} from "../accommodations.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {ReservationsService} from "../../reservations/reservations.service";
import {CommentsService} from "../../comments/comments.service";
import {MapService} from "../../map/map.service";
import {UserService} from "../../account/account.service";
import {SocketService} from "../../socket/socket.service";
import {NotificationService} from "../../notification/notification.service";
import {MaterialModule} from "../../infrastructure/material/material.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {By} from "@angular/platform-browser";

describe('AccommodationDetailsComponent', () => {
  let component: AccommodationDetailsComponent;
  let fixture: ComponentFixture<AccommodationDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccommodationDetailsComponent],
      providers: [
        AccommodationsService,
        ReservationsService,
        CommentsService,
        MapService,
        UserService,
        MatSnackBar,
        SocketService,
        NotificationService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: {
              subscribe: (fn: (value: any) => void) => fn({
                id: '1'
              }),
            },
            snapshot: {
              paramMap: convertToParamMap({
                id: '1'
              })
            }
          }
        }
      ],
      imports: [HttpClientTestingModule,MaterialModule,BrowserAnimationsModule ]
    });
    fixture = TestBed.createComponent(AccommodationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should disable the button when everything is empty",() => {
    component.form.controls['numberSelect'].setValue('');
    component.form.controls['startDateInput'].setValue('');
    component.form.controls['endDateInput'].setValue('');

    fixture.detectChanges();

    expect(component.form.valid).toBeFalsy();
    const button = fixture.debugElement.query(By.css(".reserveButton"));
    expect(button.nativeElement.disabled).toBeTruthy();
  });

    it("should disable the button when guest number select is not empty, but dates are",() => {
        component.form.controls['numberSelect'].setValue(2);
        component.form.controls['startDateInput'].setValue('');
        component.form.controls['endDateInput'].setValue('');

        fixture.detectChanges();

        expect(component.form.valid).toBeFalsy();
        const button = fixture.debugElement.query(By.css(".reserveButton"));
        expect(button.nativeElement.disabled).toBeTruthy();

    });

    it("should enable the button",() => {


       spyOn(component, 'isDateInAvailableRange').and.returnValue(true);

        component.form.controls['startDateInput'].setValue(new Date("1/19/2024"));
        component.form.controls['endDateInput'].setValue(new Date("1/20/2024"));
        component.form.controls['numberSelect'].setValue(2);

        fixture.detectChanges();

        const button = fixture.debugElement.query(By.css(".reserveButton"));
        expect(button.nativeElement.disabled).toBeFalsy();
        expect(component.form.valid).toBeTruthy();

    });


});
