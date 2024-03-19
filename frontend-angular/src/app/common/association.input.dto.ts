import { AddressDto } from "./address.dto";

export class AssociationInputDto {
    constructor(
        public businessName: string,
        public tradeName: string,
        public associationAbbreviation: string,
        public federationAbbreviation: string,
        public foundationDate: Date,
        public ein: string,
        public email: string,
        public presidentName: string,
        public address: AddressDto,
        public phoneNumbers: string[]
    ) {}
}
