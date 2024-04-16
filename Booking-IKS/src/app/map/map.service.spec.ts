import { TestBed } from '@angular/core/testing';

import { MapService } from './map.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('MapService', () => {
  let service: MapService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(MapService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
