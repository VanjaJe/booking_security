import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../env/env";
import {GuestNotificationSettings, HostNotificationSettings, Notification} from "./notification/model/model.module";

@Injectable({
  providedIn: 'root'
})

export class NotificationService {
  notifications: Notification[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getAll(id:number): Observable<Notification[]> {
    return this.httpClient.get<Notification[]>(environment.apiHost + 'notifications/' + id)
  }

  getHostSettings(id:number): Observable<HostNotificationSettings> {
    return this.httpClient.get<HostNotificationSettings>(environment.apiHost + 'notifications/host/settings/' + id)
  }

  updateHostSettings(id:number, settings: HostNotificationSettings): Observable<HostNotificationSettings> {
    return this.httpClient.put<HostNotificationSettings>(environment.apiHost + 'notifications/' + id + '/hostSettings', settings)
  }

  getGuestSettings(id:number): Observable<GuestNotificationSettings> {
    return this.httpClient.get<GuestNotificationSettings>(environment.apiHost + 'notifications/guest/settings/' + id)
  }

  updateGuestSettings(id:number, settings: GuestNotificationSettings): Observable<GuestNotificationSettings> {
    return this.httpClient.put<GuestNotificationSettings>(environment.apiHost + 'notifications/' + id + '/guestSettings', settings)
  }

  createNotification( notification: Notification): Observable<Notification> {
    return this.httpClient.post<Notification>(environment.apiHost + 'notifications',notification);
  }
}
