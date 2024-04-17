import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../env/env";
import {HttpClient, HttpParams} from "@angular/common/http";
import {CommentAndGrade} from "../administrator/comments-and-grades/model/model.module";
import {Status} from "../account/model/model.module";
import {CreateAccommodation} from "../accommodations/accommodation/model/model.module";

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private comments: CommentAndGrade[] = [];

  constructor(private httpClient: HttpClient) { }
  getAverageAccommodationRating(id: number | undefined): Observable<number> {
    return this.httpClient.get<number>(environment.apiHost + 'comments/accommodation/'+id+'/averageRate')
  }

  getAverageHostRating(hostId: number | undefined): Observable<number> {
    return this.httpClient.get<number>(environment.apiHost + 'comments/host/'+hostId+'/averageRate')
  }

  getAllForAccommodation(id: number | undefined){
    let params = new HttpParams();
    params=params.set('status','ACTIVE');
    const options={params}
    return this.httpClient.get<CommentAndGrade[]>(environment.apiHost+'comments/accommodation/'+id,options);
  }

  createHostComment(id: number , comment: CommentAndGrade): Observable<CommentAndGrade> {
    return this.httpClient.post<CommentAndGrade>(environment.apiHost+'comments/host/'+id, comment)
  }

  createAccommodationComment(id: number|undefined, comment: CommentAndGrade): Observable<CommentAndGrade> {
      return this.httpClient.post<CommentAndGrade>(environment.apiHost+'comments/accommodation/'+id, comment)
  }

  getCommentById(id: number | undefined): Observable<CommentAndGrade> {
    return this.httpClient.get<CommentAndGrade>(environment.apiHost + 'comments/'+id)
  }

  reportComment(id: number|undefined, status: Status): Observable<CommentAndGrade> {
    return this.httpClient.put<CommentAndGrade>(environment.apiHost+'comments/reportComment/'+id, status)
  }

  deleteComment(id: number|undefined): Observable<void> {
    return this.httpClient.delete<void>(environment.apiHost+'comments/'+id)
  }

  getHostComments(id: number | undefined): Observable<CommentAndGrade[]> {
    let params = new HttpParams();
    params=params.set('status','ACTIVE');
    const options={params}
    return this.httpClient.get<CommentAndGrade[]>(environment.apiHost + 'comments/host/'+id)
  }

}
