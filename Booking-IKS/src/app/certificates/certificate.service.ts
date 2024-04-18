import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../env/env";
import {Certificate, CertificateRequest, TreeNode} from "./model/model.module";

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private httpClient: HttpClient) { }

  getAll(){
    return this.httpClient.get<TreeNode>(environment.apiHostSecurity + 'certificate');
  }
  getAllRequests(){
    return this.httpClient.get<CertificateRequest[]>(environment.apiHostSecurity + 'certificateRequest');
  }

}
