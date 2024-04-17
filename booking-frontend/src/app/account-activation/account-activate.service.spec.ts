import { TestBed } from '@angular/core/testing';

import { AccountActivateService } from './account-activate.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('AccountActivateService', () => {
  let service: AccountActivateService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(AccountActivateService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
