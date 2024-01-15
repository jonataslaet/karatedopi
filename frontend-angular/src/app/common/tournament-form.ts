import { AddressForm } from "./address-form";
import { TournamentStatusEnum } from "./tournament-status-enum";

export class TournamentForm {

    constructor(
      public id: number,
      public name: string,
      public status: TournamentStatusEnum,
      public eventDateTime: Date,
      public address: AddressForm
    ){
  
    }
  }