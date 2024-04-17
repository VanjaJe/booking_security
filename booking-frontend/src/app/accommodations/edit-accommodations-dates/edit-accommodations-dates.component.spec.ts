import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAccommodationsDatesComponent } from './edit-accommodations-dates.component';

describe('EditAccommodationsDatesComponent', () => {
  let component: EditAccommodationsDatesComponent;
  let fixture: ComponentFixture<EditAccommodationsDatesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditAccommodationsDatesComponent]
    });
    fixture = TestBed.createComponent(EditAccommodationsDatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
