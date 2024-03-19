import { AddressDto } from "./address.dto";

export class FederationInputDto {
    constructor(
        public businessName: string,
        public tradeName: string,
        public federationAbbreviation: string,
        public foundationDate: Date,
        public ein: string,
        public email: string,
        public presidentName: string,
        public address: AddressDto,
        public phoneNumbers: string[]
    ) {}
}
