import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccommodationApprovalCardsComponent } from './accommodation-approval-cards.component';

describe('AccommodationApprovalCardsComponent', () => {
  let component: AccommodationApprovalCardsComponent;
  let fixture: ComponentFixture<AccommodationApprovalCardsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccommodationApprovalCardsComponent]
    });
    fixture = TestBed.createComponent(AccommodationApprovalCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
