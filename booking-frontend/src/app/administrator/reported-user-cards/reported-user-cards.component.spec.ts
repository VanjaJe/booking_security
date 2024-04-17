import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportedUserCardsComponent } from './reported-user-cards.component';

describe('ReportedUserCardsComponent', () => {
  let component: ReportedUserCardsComponent;
  let fixture: ComponentFixture<ReportedUserCardsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportedUserCardsComponent]
    });
    fixture = TestBed.createComponent(ReportedUserCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
