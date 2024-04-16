import { Injectable } from '@angular/core';
import {Accommodation, AccommodationType, Amenity} from "../accommodations/accommodation/model/model.module";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../env/env";

@Injectable({
  providedIn: 'root'
})
export class AmenitiesService {

  amenities: Amenity[] = [];

  constructor(private httpClient: HttpClient) {
  }

  getAll(): Observable<Amenity[]> {
    return this.httpClient.get<Amenity[]>(environment.apiHost + 'amenities')

   }

}
