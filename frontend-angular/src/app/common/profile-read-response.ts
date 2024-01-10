import { AddressReadResponse } from "./address-read-response";

export class ProfileReadResponse {

    constructor(
      public id: number,
      public fullname: string,
      public father: string,
      public mother: string,
      public currentBelt: string,
      public address: AddressReadResponse,
      public bloodType: string,
      public birthday: Date,
      public cpf: string,
      public rg: string,
      public phone: string
    ){
  
    }
  }