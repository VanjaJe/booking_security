import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestsViewComponent } from './requests-view.component';

describe('RequestsViewComponent', () => {
  let component: RequestsViewComponent;
  let fixture: ComponentFixture<RequestsViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequestsViewComponent]
    });
    fixture = TestBed.createComponent(RequestsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
