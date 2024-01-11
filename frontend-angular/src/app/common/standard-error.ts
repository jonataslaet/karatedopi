export class StandardError {

    constructor(
      public timestamp: number,
      public status: number,
      public error: string,
      public message: string,
      public path: string,
    ){
  
    }
  }