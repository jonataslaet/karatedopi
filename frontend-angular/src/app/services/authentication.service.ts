import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { Observable } from 'rxjs';
import { endpoints } from '../common/app.endpoints';
import { AuthenticationResponse } from '../common/authentication.response';
import { CredentialsDTO } from '../common/credentials.dto';
import { EmailDTO } from '../common/email.dto';
import { PasswordResetDTO } from '../common/password.reset.dto';
import { RouteItem } from '../common/route.item';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  
  currentUserSignal = signal<AuthenticationResponse | undefined | null>(
    undefined
  );
  currentMenusByRole = signal<RouteItem[] | undefined | null>(undefined);

  constructor(private requestService: RequestService, private router: Router) {}

  authenticate(
    credentials: CredentialsDTO
  ): Observable<AuthenticationResponse> {
    return this.requestService.request('POST', '/login', credentials);
  }

  getAuthenticatedUser(): Observable<AuthenticationResponse> {
    return this.requestService.request('GET', '/user');
  }

  recoveryToken(
    email: EmailDTO
  ): Observable<string> {
    return this.requestService.request('POST', '/recovery-token', email);
  }

  renewPassword(
    token: string,
    passwordReset: PasswordResetDTO
  ): Observable<void> {
    const endpoint = `/new-password/${token}`;
    return this.requestService.request('POST', endpoint, passwordReset);
  }

  getAuthenticatedUserByToken(token: string): Observable<AuthenticationResponse> {
    const endpoint = `/user/token/${token}`;
    return this.requestService.request('GET', endpoint);
  }

  get isLoggedIn(): boolean {
    return this.requestService.isLoggedIn;
  }

  get authenticatedToken(): string {
    return this.requestService.getAuthToken();
  }

  logoutAndStartFromLanding(): void {
    this.requestService.setAuthToken(null);
    this.currentUserSignal.set(null);
    this.currentMenusByRole.set(null);
    this.router.navigate(['/landing']);
  }

  setAuthToken(token: string) {
    this.requestService.setAuthToken(token);
  }

  loadAuthenticationResponse(authenticationResponse: AuthenticationResponse) {
    this.currentUserSignal.set(authenticationResponse);
    this.setCurrentMenusByRole();
    this.setAuthToken(authenticationResponse.accessToken);
  }

  setCurrentUser(): void {
    this.currentUserSignal.set(this.getLoggedUser());
  }

  setCurrentMenusByRole(): void {
    this.currentMenusByRole.set(this.getRouteMenuItemsByRole(this.getAuthenticatedAuthorities()));
  }

  getRouteMenuItemsByRole(authorities: string[]): RouteItem[] {
    let menusByRole: RouteItem[] = [];
    endpoints.routes.forEach((route: RouteItem) => {
      if (route.authorities.some((authority) => authorities.includes(authority)) && route.isMenu) {
        menusByRole.push(route);
      }
    });
    return menusByRole;
  }

  getAuthToken(): string {
    return this.requestService.getAuthToken();
  }

  getAuthenticatedAuthorities(): string[] {
    const token = localStorage.getItem('auth_token') ?? '';
    if (token && token !== null && token.length > 7) {
      return jwtDecode(token)['authorities'];
    }
    return [];
  }

  getLoggedUser(): AuthenticationResponse {
    const token = localStorage.getItem('auth_token') ?? '';
    if (token && token !== null && token.length > 7) {
      let authenticationResponse = {
        id: jwtDecode(token)['id'],
        firstname: jwtDecode(token)['firstname'],
        lastname: jwtDecode(token)['lastname'],
        email: jwtDecode(token)['email'],
        accessToken: jwtDecode(token)['accessToken'],
        authorities: jwtDecode(token)['authorities']
      };
      return authenticationResponse;
    }
    this.logoutAndStartFromLanding();
    return null;
  }

  hasPrivileges(uri: string): boolean {
    let pathAuthorities: string[] = this.getAuthoritiesForPath(uri);
    return pathAuthorities.some(
      (authority) => this.getAuthenticatedAuthorities().includes(authority)
    );
  }

  getAuthoritiesForPath(path: string): string[] {
    const matchingMenu = endpoints.routes.find((menu: RouteItem) => {
        const regex = new RegExp(`^${menu.path.replace(/:\w+/g, '[^/]+')}$`);
        return regex.test(path);
    });
    if (matchingMenu) {
        return matchingMenu.authorities || [];
    }
    return [];
  }
}
