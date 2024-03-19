import { AddressDto } from "./address.dto";

export class AssociationOutputDto {
    constructor(
        public id: number,
        public businessName: string,
        public tradeName: string,
        public associationAbbreviation: string,
        public federationAbbreviation: string,
        public foundationDate: Date,
        public ein: string,
        public email: string,
        public status: string,
        public presidentName: string,
        public federationId: number,
        public address: AddressDto,
        public phoneNumbers: string[]
    ) {}
}
