import { TestBed } from '@angular/core/testing';

import { AmenitiesService } from './amenities.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('AmenitiesService', () => {
  let service: AmenitiesService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(AmenitiesService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
