import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateDialogComponent } from './certificate-dialog.component';

describe('CertificateDialogComponent', () => {
  let component: CertificateDialogComponent;
  let fixture: ComponentFixture<CertificateDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CertificateDialogComponent]
    });
    fixture = TestBed.createComponent(CertificateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
