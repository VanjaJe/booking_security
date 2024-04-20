import {User} from "../../account/model/model.module";

export interface Certificate {
  id: number;
  certificateType: CertificateType;
  dateFrom: Date;
  dateTo: Date;
  email: string;
  serialNumber: string;
  issuerSerialNumber: string;
}
export enum RequestStatus {
  ACTIVE = 'ACTIVE',
  ACCEPTED = 'ACCEPTED',
  DECLINED = 'DECLINED'
}

export enum CertificateType {
  ROOT = 'ROOT',
  INTERMEDIATE = 'INTERMEDIATE',
  END_ENTITY = 'END_ENTITY'
}

export interface CertificateRequest{
  id: number;
  subject: User;
  certificateIssuer: Certificate;
  date: Date;
  requestStatus: RequestStatus;
  certificateType: CertificateType;
}

export interface TreeNode {
  name: string;
  children?: TreeNode[];
}
