import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../common/authentication-response';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  currentUserSignal = signal<AuthenticationResponse | undefined | null>(undefined);

  constructor(private requestService: RequestService) { }

  getAuthenticatedUser(): Observable<AuthenticationResponse> {
    return this.requestService.request('GET', '/user');
  }
}
