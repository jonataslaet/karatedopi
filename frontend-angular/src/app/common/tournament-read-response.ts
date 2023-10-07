export class TournamentReadResponse {

    constructor(
      public id: number,
      public name: string,
      public location: string,
      public status: string,
      public numberOfParticipants: number,
      public eventDate: Date,
      public eventTime: string,
    ){
  
    }
  }