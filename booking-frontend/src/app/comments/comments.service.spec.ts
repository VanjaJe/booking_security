import { TestBed } from '@angular/core/testing';

import { CommentsService } from './comments.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('CommentsService', () => {
  let service: CommentsService;
  let httpController: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(CommentsService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
