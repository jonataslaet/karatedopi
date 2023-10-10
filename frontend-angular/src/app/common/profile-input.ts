import { AddressCreateDTO } from "./address-create-dto";

export class ProfileInput {

    constructor(
      public id: number,
      public fullname: string,
      public father: string,
      public mother: string,
      public address: AddressCreateDTO,
      public bloodType: string,
      public birthday: Date,
      public cpf: string, 
      public rg: string,
      public phone: string
    ){
  
    }
  }