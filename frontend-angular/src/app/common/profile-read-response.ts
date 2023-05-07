export class ProfileReadResponse {

    constructor(
      public id: number,
      public fullname: string,
      public father: string,
      public mother: string,
      public zipCode: string,
      public street: string,
      public number: string,
      public neighbourhood: string,
      public city: string,
      public state: string,
      public bloodType: string,
      public birthday: Date,
      public cpf: string, 
      public rg: string,
      public phone: string
    ){
  
    }
  }