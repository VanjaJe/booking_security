import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Accommodation, TimeSlot} from "../accommodations/accommodation/model/model.module";
import {environment} from "../../env/env";
import {AccommodationReport} from "./model/model";
import {DatePipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private httpClient: HttpClient) {
  }
  private formatDate(date: Date): string {
    return <string>new DatePipe('en-US').transform(date, 'yyyy-MM-dd');
  }
  getAnnualReport(accommodationName:string,year:number): Observable<AccommodationReport> {
    let params=new HttpParams();
    params=params.set('accommodationName',accommodationName);
    params=params.set('year',year);
    return this.httpClient.get<AccommodationReport>(environment.apiHost + 'reports/annual',{params});
  }
  getTimeSlotRepost(hostId:number, startDate: Date, endDate: Date,):Observable<AccommodationReport[]> {
    let params=new HttpParams();
    params = params.set('begin', this.formatDate(startDate));
    params = params.set('end', this.formatDate(endDate));
    params=params.set('hostId',hostId);
    return this.httpClient.get<AccommodationReport[]>(environment.apiHost + 'reports',{params});

  }

}
