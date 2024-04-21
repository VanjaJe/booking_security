import {User} from "../../account/model/model.module";

export interface Certificate {
  id: number;
  certificateType: CertificateType;
  dateFrom: Date;
  dateTo: Date;
  email: string;
  serialNumber: string;
  issuerSerialNumber: string;
  revokeReason?:string
  keyUsages?: KeyUsages [];
}

export enum CertificateRequestStatus {
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
  id?: number;
  subject?: SubjectData;
  issuerSerialNumber?: string;
  date?: Date;
  requestStatus?: CertificateRequestStatus;
  certificateType?: CertificateType;
  keyUsages?: KeyUsages [];
}

export interface SubjectData {
  id: number;
  email: string;
  name: string;
  lastname: string;
}

export interface TreeNode {
  name: string;
  children?: TreeNode[];
}
export enum KeyUsages {
  DIGITAL_SIGNATURE = 'DIGITAL_SIGNATURE',
  CERTIFICATE_SIGNING = 'CERTIFICATE_SIGNING',
  NON_REPUDIATION = 'NON_REPUDIATION',
  KEY_ENCIPHERMENT = 'KEY_ENCIPHERMENT',
  DATA_ENCIPHERMENT = 'DATA_ENCIPHERMENT',
  KEY_AGREEMENT = 'KEY_AGREEMENT',
  CRL_SIGNING = 'CRL_SIGNING',
  ENCRYPT_ONLY = 'ENCRYPT_ONLY',
}

export enum CertificateTemplate {
  CERTIFICATE_AUTHORITY="CERTIFICATE_AUTHORITY",
  SSL_TLS_Client="SSL_TLS_Client" ,
  SSL_TLS_Server="SSL_TLS_Server",
  Code_Signing_Certificate="Code_Signing_Certificate",
  Email_Signing_Certificate="Email_Signing_Certificate"
}
