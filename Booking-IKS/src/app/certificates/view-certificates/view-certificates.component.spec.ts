import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCertificatesComponent } from './view-certificates.component';

describe('ViewCertificatesComponent', () => {
  let component: ViewCertificatesComponent;
  let fixture: ComponentFixture<ViewCertificatesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewCertificatesComponent]
    });
    fixture = TestBed.createComponent(ViewCertificatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
