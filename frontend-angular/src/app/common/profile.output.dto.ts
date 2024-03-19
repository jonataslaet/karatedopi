
export class ProfileOutputDto {
    constructor(
        public id: number,
        public fullname: string,
        public father: string,
        public mother: string,
        public bloodType: string,
        public birthday: Date,
        public itin: string,
        public nid: string,
        public associationAbbreviation: string,
        public phoneNumbers: string[],
        public currentBelt: string
    ) {}
}