import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../common/authentication-response';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  currentUserSignal = signal<AuthenticationResponse | undefined | null>(undefined);

  constructor(private requestService: RequestService, private router: Router) { }

  getAuthenticatedUser(): Observable<AuthenticationResponse> {
    return this.requestService.request('GET', '/user');
  }

  startFromLogin(): void {
    localStorage.setItem('auth_token', '');
    this.currentUserSignal.set(null);
    this.router.navigate(['/login']);
  }
}
