export class UserOutputDto {
    constructor(
        public id: number,
        public firstname: string,
        public lastname: string,
        public authority: string,
        public email: string,
        public status: string
    ) {}
}
