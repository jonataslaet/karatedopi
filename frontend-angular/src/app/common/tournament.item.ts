import { AddressDto } from "./address.dto";
import { TournamentParticipant } from "./tournament.participant";
import { TournamentStatusEnum } from "./tournament.status.enum";

export class TournamentItem {
    constructor(
        public id: number,
        public name: string,
        public address: AddressDto,
        public status: TournamentStatusEnum,
        public participants: TournamentParticipant[],
        public eventDateTime: Date
    ) {}
}
