import {User} from "../../account/model/model.module";

export interface Certificate {
  id: number;
  certificateType: string;
  dateFrom: Date;
  dateTo: Date;
  subject: User;
  isRevoked: boolean;
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
