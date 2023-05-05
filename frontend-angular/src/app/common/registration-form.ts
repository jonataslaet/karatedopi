export class RegistrationForm {

    constructor(public id: number,
      public email: string,
      public password: string,
      public firstname: string,
      public lastname: string,
      public father: string,
      public mother: string,
      public hometown: string,
      public birthday: Date,
      public cpf: string,
      public rg: string,
      public phone: string
    ){
  
    }
  }