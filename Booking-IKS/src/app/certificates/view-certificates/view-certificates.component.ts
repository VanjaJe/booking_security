import {Component, OnInit} from '@angular/core';
import {CertificateRequest, CertificateRequestStatus, CertificateType, TreeNode} from "../model/model.module";
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from "@angular/material/tree";
import {ActivatedRoute} from "@angular/router";
import {CertificateService} from "../certificate.service";

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
  constructor(private root:ActivatedRoute,private certificateService: CertificateService) { }
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
}

