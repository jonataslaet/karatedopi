
export class ProfileInputDto {
    constructor(
        public fullname: string,
        public father: string,
        public mother: string,
        public bloodType: string,
        public birthday: Date,
        public itin: string,
        public nid: string,
        public nidPhotoUrl: string,
        public associationAbbreviation: string,
        public phoneNumbers: string[]
    ) {}
}