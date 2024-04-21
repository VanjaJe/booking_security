import {Component, OnInit} from '@angular/core';
import {
  Certificate,
  CertificateRequest,
  CertificateRequestStatus,
  CertificateType,
  TreeNode
} from "../model/model.module";
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from "@angular/material/tree";
import {ActivatedRoute} from "@angular/router";
import {CertificateService} from "../certificate.service";
import {MatDialog} from "@angular/material/dialog";
import {CertificateDialogComponent} from "../certificate-dialog/certificate-dialog.component";

@Component({
  selector: 'app-view-certificates',
  templateUrl: './view-certificates.component.html',
  styleUrls: ['./view-certificates.component.css']
})
export class ViewCertificatesComponent implements OnInit{

  treeControl = new NestedTreeControl<TreeNode>(node => node.children);
  treeDataSource = new MatTreeNestedDataSource<TreeNode>();
  certificate:CertificateRequest;
  id:number;
  constructor(private root:ActivatedRoute,private certificateService: CertificateService,
private dialog: MatDialog) { }
  ngOnInit(): void {
    this.root.paramMap.subscribe(params => {
      // @ts-ignore
      this.id = +params.get('id');
      if(this.id!=0){
        this.certificateService.getRequestById(this.id).subscribe({
          next: (data: CertificateRequest) => {
            this.certificate=data
            console.log(this.certificate)

          },
          error: (_: any) => {
            console.log("Error fetching data from CertificateService");
          }
        });
      }


    });
      this.certificateService.getAll().subscribe(rootNode => {
        // @ts-ignore
        this.treeDataSource.data = [rootNode];
      });
    }
    hasChild = (_: number, node: TreeNode) => !!node.children && node.children.length > 0;

  signCertificate(serialNumber: string) {
    //&& this.certificate.certificateType!=CertificateType.END_ENTITY
    if(this.certificate!=null ){
      this.certificate.requestStatus=CertificateRequestStatus.ACCEPTED
      this.certificate.issuerSerialNumber=serialNumber
      this.certificateService.updateCertificateRequest(this.certificate).subscribe({
        next: (data: CertificateRequest) => {
        },
        error: (_: any) => {
          console.log("Error fetching data from CertificateService");
        }
      });
    }

  }
  deleteCertificate(serialNumber: string) {
    this.certificateService.deleteCertificate(serialNumber).subscribe({
      next: (data:string) => {
      },
      error: (_: any) => {
        console.log("Error deleting data from CertificateService");
      }
    });
  }

  showDetails(certificate: Certificate) {
    this.dialog.open(CertificateDialogComponent, {
      width: '500px',
      height:'400px',
      data: certificate
    });

  }
}

