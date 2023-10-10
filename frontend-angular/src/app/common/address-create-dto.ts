export class AddressCreateDTO {

    constructor(
      public zipCode: string,
      public street: string,
      public number: string,
      public neighbourhood: string,
      public city: string,
      public state: string
    ){
  
    }
  }