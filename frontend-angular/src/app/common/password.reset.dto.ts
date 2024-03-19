export class PasswordResetDTO {
    constructor(public newPassword: string, public newPasswordConfirmation: string) {}
}
