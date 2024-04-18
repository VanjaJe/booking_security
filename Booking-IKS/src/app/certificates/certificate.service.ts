import { Injectable } from '@angular/core';
import {RequestStatus, ReservationRequest} from "../accommodations/accommodation/model/model.module";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../env/env";
import {Certificate, CertificateRequest} from "./model/model.module";

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private httpClient: HttpClient) { }

  getAll(){
    return this.httpClient.get<Certificate[]>(environment.apiHostSecurity + 'certificate');
  }
  getAllRequests(){
    return this.httpClient.get<CertificateRequest[]>(environment.apiHostSecurity + 'certificateRequest');
  }

}
