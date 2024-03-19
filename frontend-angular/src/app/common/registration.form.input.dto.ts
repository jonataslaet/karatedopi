import { AddressDto } from "./address.dto";
import { ProfileInputDto } from "./profile.input.dto";
import { UserInputDto } from "./user.input.dto";

export class RegistrationFormInputDto {
    constructor(
        public user: UserInputDto,
        public profile: ProfileInputDto,
        public address: AddressDto
    ) {}
}
