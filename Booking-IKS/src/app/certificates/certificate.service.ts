import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../env/env";
import {Certificate, CertificateRequest, TreeNode} from "./model/model.module";
import {ReservationRequest} from "../accommodations/accommodation/model/model.module";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private httpClient: HttpClient) { }

  getAll(){
    return this.httpClient.get<TreeNode>(environment.apiHostSecurity + 'certificate');
  }
  getRequestById(id:number){
    return this.httpClient.get<CertificateRequest>(environment.apiHostSecurity + 'certificateRequest/'+id);
  }
  getAllRequests(){
    return this.httpClient.get<CertificateRequest[]>(environment.apiHostSecurity + 'certificateRequest');
  }
  createCertificateRequest(request: CertificateRequest): Observable<CertificateRequest> {
    return this.httpClient.post<CertificateRequest>(environment.apiHostSecurity + 'certificateRequest', request);
  }
  updateCertificateRequest(request: CertificateRequest): Observable<CertificateRequest> {
    return this.httpClient.put<CertificateRequest>(environment.apiHostSecurity + 'certificateRequest', request);
  }

}
