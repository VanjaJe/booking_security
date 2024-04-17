import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, endWith, forkJoin, map } from 'rxjs';
import { environment } from 'src/env/env';
import { CommentAndGrade } from './comments-and-grades/model/model.module';
import { User } from '../account/model/model.module';


@Injectable({
  providedIn: 'root'
})

export class CommentAndGradeService {
  private commentAndGradeList: Comment[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getAllReportedAndPanding(): Observable<CommentAndGrade[]> {
    const firstRequest = this.httpClient.get<CommentAndGrade[]>(environment.apiHost + 'comments/accommodations?status=PENDING');
    const secondRequest = this.httpClient.get<CommentAndGrade[]>(environment.apiHost + 'comments/hosts?status=PENDING');
    const firstRequest1 = this.httpClient.get<CommentAndGrade[]>(environment.apiHost + 'comments/accommodations?status=REPORTED');
    const secondRequest1 = this.httpClient.get<CommentAndGrade[]>(environment.apiHost + 'comments/hosts?status=REPORTED');
    return forkJoin([firstRequest, secondRequest, firstRequest1,secondRequest1]).pipe(
      map(([result1, result2, result3, result4]) => result1.concat(result2).concat(result3).concat(result4))
    );
  }

  add(comment: CommentAndGrade): Observable<CommentAndGrade> {
    return this.httpClient.post<CommentAndGrade>(environment.apiHost + 'add', comment)
  }

  getCommentAndGrade(id: number): Observable<CommentAndGrade> {
    return this.httpClient.get<CommentAndGrade>(environment.apiHost + 'comments/' + id)
  }

  approve(commentAndGrade: CommentAndGrade): Observable<CommentAndGrade>{
    if(commentAndGrade.accommodation){
      return this.httpClient.put<CommentAndGrade>(environment.apiHost + 'comments/approve/accommodations/'+ commentAndGrade.id, commentAndGrade)
    }
    else{
      console.log(commentAndGrade)
      return this.httpClient.put<CommentAndGrade>(environment.apiHost + 'comments/approve/hosts/'+ commentAndGrade.id, commentAndGrade)
    }
  }

  decline(commentAndGrade: CommentAndGrade): Observable<CommentAndGrade>{
    if(commentAndGrade.accommodation){
      return this.httpClient.put<CommentAndGrade>(environment.apiHost + 'comments/decline/accommodations/' + commentAndGrade.id,commentAndGrade)
    }
    else{
      return this.httpClient.put<CommentAndGrade>(environment.apiHost + 'comments/decline/hosts/' + commentAndGrade.id,commentAndGrade)
    }
  }

}
