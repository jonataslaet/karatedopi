import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../common/authentication-response';
import { CredentialsDTO } from '../common/credentials-dto';
import { MenuItem } from '../common/menu-item';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  
  currentUserSignal = signal<AuthenticationResponse | undefined | null>(
    undefined
  );
  currentMenusByRole = signal<MenuItem[] | undefined | null>(undefined);

  constructor(private requestService: RequestService, private router: Router) {}

  authenticate(
    credentials: CredentialsDTO
  ): Observable<AuthenticationResponse> {
    return this.requestService.request('POST', '/login', credentials);
  }

  getAuthenticatedUser(): Observable<AuthenticationResponse> {
    return this.requestService.request('GET', '/user');
  }

  get isLoggedIn(): boolean {
    return this.requestService.isLoggedIn;
  }

  get authenticatedToken(): string {
    return this.requestService.getAuthToken();
  }

  startFromLogin(): void {
    this.requestService.setAuthToken(null);
    this.currentUserSignal.set(null);
    this.currentMenusByRole.set(null);
    this.router.navigate(['/login']);
  }

  setAuthToken(token: string) {
    this.requestService.setAuthToken(token);
  }

  getAuthToken(): string {
    return this.requestService.getAuthToken();
  }
}
