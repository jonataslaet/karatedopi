export class ProfileReadResponse {

    constructor(public id: number,
      public firstname: string,
      public lastname: string,
      public father: string,
      public mother: string,
      public hometown: string,
      public birthday: Date,
      public cpf: string,
      public rg: string
    ){
  
    }
  }