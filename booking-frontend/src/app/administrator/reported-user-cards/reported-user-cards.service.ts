import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Status, User } from 'src/app/account/model/model.module';
import { environment } from 'src/env/env';

@Injectable({
  providedIn: 'root'
})

export class ReportedUserService {
  private reportedUserList: User[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getUsersByStatus(status: string): Observable<User[]> {
    let params = new HttpParams();
    params = params.set('status', status);
    const options = { params: params };
    return this.httpClient.get<User[]>(environment.apiHost + 'users/status', options);
  }
  
  add(reportedUser: User): Observable<User> {
    return this.httpClient.post<User>(environment.apiHost + 'add', reportedUser)
  }

  getCommentAndGrade(id: number): Observable<User> {
    return this.httpClient.get<User>(environment.apiHost + 'users/' + id)
  }
}