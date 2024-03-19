import { CityDto } from "./city.dto";

export class AddressDto {
    constructor(
        public id: number,
        public zipCode: string,
        public street: string,
        public number: string,
        public neighbourhood: string,
        public city: CityDto
    ) {}
}
