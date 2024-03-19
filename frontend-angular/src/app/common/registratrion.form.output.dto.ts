import { AddressDto } from "./address.dto";
import { ProfileOutputDto } from "./profile.output.dto";
import { UserOutputDto } from "./user.output.dto";

export class RegistratrionFormOutputDto {
    constructor(
        public user: UserOutputDto,
        public profile: ProfileOutputDto,
        public address: AddressDto
    ) {}
}
