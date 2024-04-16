import { TestBed } from '@angular/core/testing';

import { CommentAndGradeService } from './administrator.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('AdministratorService', () => {
  let service: CommentAndGradeService;
  let httpController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(CommentAndGradeService);
    httpController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
