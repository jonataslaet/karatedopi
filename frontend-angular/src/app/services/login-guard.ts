import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';

export const LoginGuard: CanActivateFn = () => {
  const authenticationService = inject(AuthenticationService);
  return authenticationService.isLoggedIn ? inject(Router).navigate(['/home']) : true;
}