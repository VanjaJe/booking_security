import { Injectable } from '@angular/core';
import {Amenity} from "./amenity/model/model.module";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../env/env";


@Injectable({
  providedIn: 'root'
})
export class AmenityService {
  amenities: Amenity[] = []

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Amenity[]> {
    return this.httpClient.get<Amenity[]>(environment.apiHost + 'amenities')
  }

}
