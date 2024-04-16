import { TestBed } from '@angular/core/testing';

import { AmenityService } from './amenity.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('AmenityService', () => {
  let service: AmenityService;
  let httpController: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(AmenityService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
