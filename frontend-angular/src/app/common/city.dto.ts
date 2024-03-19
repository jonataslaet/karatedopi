import { StateDto } from "./state.dto";

export class CityDto {
    constructor(public id: number, public name: string, public state: StateDto) {}
}
