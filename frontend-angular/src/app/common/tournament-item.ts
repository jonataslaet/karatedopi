export class TournamentItem {

    constructor(
      public id: number,
      public name: string,
      public location: string,
      public status: string,
      public idsParticipants: number[],
      public eventDate: Date,
      public eventTime: string,
    ){
  
    }
  }