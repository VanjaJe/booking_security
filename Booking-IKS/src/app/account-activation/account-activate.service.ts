import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Accommodation} from "../accommodations/accommodation/model/model.module";
import {environment} from "../../env/env";
import {ActivatedRoute} from "@angular/router";
import {AccommodationsService} from "../accommodations/accommodations.service";
import {ReservationsService} from "../reservations/reservations.service";
import {CommentsService} from "../comments/comments.service";
import {MapService} from "../map/map.service";

@Injectable({
  providedIn: 'root'
})
export class AccountActivateService {
  constructor(private httpClient: HttpClient) {
  }

  activateAccount(activateLink: string, username: string): Observable<string> {
    return this.httpClient.get<string>(environment.apiHost + 'users/user-account-activation/' + activateLink + "/" + username)
  }
}
