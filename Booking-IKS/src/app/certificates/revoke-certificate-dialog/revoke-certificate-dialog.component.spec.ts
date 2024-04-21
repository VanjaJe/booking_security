import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevokeCertificateDialogComponent } from './revoke-certificate-dialog.component';

describe('RevokeCertificateDialogComponent', () => {
  let component: RevokeCertificateDialogComponent;
  let fixture: ComponentFixture<RevokeCertificateDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RevokeCertificateDialogComponent]
    });
    fixture = TestBed.createComponent(RevokeCertificateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
