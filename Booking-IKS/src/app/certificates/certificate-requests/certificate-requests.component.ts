import {Component, ViewChild} from '@angular/core';
import {Certificate, CertificateRequest} from "../model/model.module";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {CertificateService} from "../certificate.service";

@Component({
  selector: 'app-certificate-requests',
  templateUrl: './certificate-requests.component.html',
  styleUrls: ['./certificate-requests.component.css']
})
export class CertificateRequestsComponent {
  certificates: CertificateRequest[] = [];
  dataSource = new MatTableDataSource<CertificateRequest>([]);
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  displayedColumns = ['date', 'status', 'username'];

  constructor(private certificateRequestService:CertificateService) {
  }


  ngOnInit(): void {
    this.certificateRequestService.getAllRequests().subscribe({
      next: (data: CertificateRequest[]) => {
        this.certificates = data;
        this.dataSource = new MatTableDataSource<CertificateRequest>(this.certificates);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator=this.paginator;
      },
      error: (_: any) => {
        console.log("Error fetching data from CertificateService");
      }
    });

  }


}
