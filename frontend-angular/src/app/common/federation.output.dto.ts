import { AddressDto } from "./address.dto";
import { OrganizationStatusEnum } from "./organization.status.enum";

export class FederationOutputDto {
    constructor(
        public id: number,
        public businessName: string,
        public tradeName: string,
        public federationAbbreviation: string,
        public foundationDate: Date,
        public ein: string,
        public email: string,
        public status: OrganizationStatusEnum,
        public presidentName: string,
        public address: AddressDto,
        public phoneNumbers: string[]
    ) {}
}
