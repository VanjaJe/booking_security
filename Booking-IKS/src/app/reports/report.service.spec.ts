import { TestBed } from '@angular/core/testing';

import { ReportService } from './report.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('ReportService', () => {
  let service: ReportService;
  let httpController: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(ReportService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
