import { TestBed } from '@angular/core/testing';

import { AccommodationsService } from './accommodations.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('AccommodationsService', () => {
  let service: AccommodationsService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(AccommodationsService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
