import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Certificate, CertificateRequest} from "../model/model.module";
import {MatSort} from "@angular/material/sort";
import {CertificateService} from "../certificate.service";
import {MatPaginator} from "@angular/material/paginator";
@Component({
  selector: 'app-view-certificates',
  templateUrl: './view-certificates.component.html',
  styleUrls: ['./view-certificates.component.css']
})
export class ViewCertificatesComponent implements OnInit{
  ngOnInit(): void {
  }


}
