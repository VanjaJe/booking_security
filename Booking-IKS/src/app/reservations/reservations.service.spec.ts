import { TestBed } from '@angular/core/testing';

import { ReservationsService } from './reservations.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {mockRequest1} from "../mocks/reservations.service.mock";

describe('ReservationsService', () => {
  let service: ReservationsService;
  let httpController: HttpTestingController;

  let url = 'http://localhost:8080/api';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(ReservationsService);
    httpController = TestBed.inject(HttpTestingController);
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call add and the API should return the request that was added', () => {

    service.add(mockRequest1).subscribe((data) => {
      expect(data).toEqual(mockRequest1);
    });

    const req = httpController.expectOne({
      method: 'POST',
      url: `${url}/requests`,
    });

    req.flush(mockRequest1);
  });

});
