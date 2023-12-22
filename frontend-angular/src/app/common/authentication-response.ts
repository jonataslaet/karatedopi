export class AuthenticationResponse {
  constructor(public id: number, public firstname: string, public lastname: string, public email: string, public accessToken: string, public authorities: string[]) {}
}
