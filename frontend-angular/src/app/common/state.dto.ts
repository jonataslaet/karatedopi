import { StateAbbreviation } from "./state.abbreviation.enum";

export class StateDto {
    constructor(public id: number, public name: string, public stateAbbreviation: StateAbbreviation) {}
}
