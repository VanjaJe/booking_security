import { Injectable } from '@angular/core';
import {Accommodation, FavoriteAccommodations} from "../../accommodations/accommodation/model/model.module";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../env/env";

@Injectable({
  providedIn: 'root'
})
export class FavoritesService {

  favorites: FavoriteAccommodations[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getAllforGuest(): Observable<FavoriteAccommodations[]> {
    return this.httpClient.get<FavoriteAccommodations[]>(environment.apiHost + 'favoriteAccommodations/guest/1')
  }

}
