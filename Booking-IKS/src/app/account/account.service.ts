import {Injectable} from '@angular/core';
import {Account, User, Address, Status, UserProfile} from './model/model.module';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { environment } from 'src/env/env';
import {JwtHelperService} from "@auth0/angular-jwt";
import {Guest} from "../administrator/comments-and-grades/model/model.module";
import {KeycloakService} from "../certificates/keycloak.service";
import {keyframes} from "@angular/animations";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  users: User[] = [];

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  user$ = new BehaviorSubject("");
  userState = this.user$.asObservable();

  constructor(private httpClient: HttpClient, private keycloak: KeycloakService) {

    console.log("rolica ",this.getRole())
    this.user$.next(this.getRole());
  }
  login(auth: any): Observable<any> {
    return this.httpClient.post(environment.apiHost+'users/login', {username: auth.username, password: auth.password}, {headers: this.headers, responseType: 'json'});
  }

  getAll(): Observable<User[]> {
    return this.httpClient.get<User[]>(environment.apiHost + 'users')
  }

  add(user: User): Observable<User> {
    return this.httpClient.post<User>(environment.apiHost + 'add', user)
  }


  update(user: User): Observable<User> {
    const url = `${environment.apiHost}users/${user.id}`;
    return this.httpClient.put<User>(url, user);
  }


  getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(environment.apiHost + 'users/' + id)
  }

  getUserByUsername(username: number): Observable<User> {
    return this.httpClient.get<User>(environment.apiHost + 'users/userEmail/' + username)
  }

  delete(id: number): Observable<void> {
    console.log("Uslo")
    const url = `${environment.apiHost}users/${id}`;
    return this.httpClient.delete<void>(url);
  }

  signup(user: User): Observable<User> {
    return this.httpClient.post<User>(environment.apiHost + 'users/signup', user);
  }

  sendEmail(user: User): Observable<string> {
    const userId = user.id;
    // @ts-ignore
    let params = new HttpParams().set('username', user.account.username);
    const options = { params };
    return this.httpClient.get<string>(environment.apiHost + 'email/send', options);
  }
  isLoggedIn(): boolean {
    return localStorage.getItem('user') != null;
  }
  getRole(): any {
    if (this.keycloak.keycloak?.authenticated) {
      // const accessToken: any = localStorage.getItem('user');
      const accessToken: any = this.keycloak.keycloak.token;
      // const accessToken: any = this.profile.token;
      const helper = new JwtHelperService();
      const decodedToken=helper.decodeToken(accessToken);
      const realmAccess = decodedToken.realm_access;
      console.log("roli ",realmAccess)
      if (realmAccess && realmAccess.roles && realmAccess.roles.includes('HOST')) {
        return "ROLE_HOST"
      } else if(realmAccess && realmAccess.roles && realmAccess.roles.includes('GUEST')){
        return "ROLE_GUEST"
      }
      // return helper.decodeToken(accessToken).role;
    }
    return null;
  }
  // getRoleName(): any {
  //   if (this.isLoggedIn()) {
  //     const accessToken: any = localStorage.getItem('user');
  //     const helper = new JwtHelperService();
  //     return helper.decodeToken(accessToken).role.name;
  //   }
  //   return null;
  // }
  getUserId(): any {
    if (this.keycloak.keycloak?.authenticated) {
      // const accessToken: any = localStorage.getItem('user');
      const accessToken: any = this.keycloak.keycloak.token;

      const helper = new JwtHelperService();
      return helper.decodeToken(accessToken).email;
    }
    return null;
  }
  setUser(): void {
    this.user$.next(this.getRole());
  }

  uploadImage(files: File[], id: number): Observable<User> {
    const data: FormData = new FormData();
    for (let file of files) {
      data.append("images", file);
    }
    return this.httpClient.post<User>(environment.apiHost + "users/" + id + "/upload-picture", data)
  }


  getImages(id: number | undefined): Observable<string[]> {
    return this.httpClient.get<string[]>(environment.apiHost + 'users/' + id + '/images');
  }

  block(user: User) {
    const url = `${environment.apiHost}users/block/${user.id}`;
    return this.httpClient.put<User>(url, user);
  }


  reportHost(guestId: number|undefined, reportedHost: User): Observable<User> {
    return this.httpClient.put<User>(environment.apiHost + 'users/reportUser/' + guestId, reportedHost);
  }

  reportGuest(hostId: number|undefined, reportedGuest: User): Observable<User> {
    return this.httpClient.put<User>(environment.apiHost + 'users/reportUser/' + hostId, reportedGuest);
  }
}
