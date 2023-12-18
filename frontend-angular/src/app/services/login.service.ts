import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../common/authentication-response';
import { CredentialsDTO } from '../common/credentials-dto';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private requestService: RequestService) {}

  authenticate(
    credentials: CredentialsDTO
  ): Observable<AuthenticationResponse> {
    return this.requestService.request('POST', '/login', credentials);
  }

  setAuthToken(token: string) {
    this.requestService.setAuthToken(token);
  }
}
