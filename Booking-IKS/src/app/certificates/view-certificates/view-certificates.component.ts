import {Component, OnInit, ViewChild} from '@angular/core';
import { TreeNode} from "../model/model.module";
import {CertificateService} from "../certificate.service";
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from "@angular/material/tree";

@Component({
  selector: 'app-view-certificates',
  templateUrl: './view-certificates.component.html',
  styleUrls: ['./view-certificates.component.css']
})
export class ViewCertificatesComponent implements OnInit{

  treeControl = new NestedTreeControl<TreeNode>(node => node.children);
  treeDataSource = new MatTreeNestedDataSource<TreeNode>();
  constructor(private certificateService: CertificateService) { }
  ngOnInit(): void {
      this.certificateService.getAll().subscribe(rootNode => {
        // @ts-ignore
        this.treeDataSource.data = [rootNode];
      });
    }
    hasChild = (_: number, node: TreeNode) => !!node.children && node.children.length > 0;
  }

