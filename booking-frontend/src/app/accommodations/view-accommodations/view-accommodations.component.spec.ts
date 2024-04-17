import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAccommodationsComponent } from './view-accommodations.component';

describe('ViewAccommodationsComponent', () => {
  let component: ViewAccommodationsComponent;
  let fixture: ComponentFixture<ViewAccommodationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewAccommodationsComponent]
    });
    fixture = TestBed.createComponent(ViewAccommodationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
