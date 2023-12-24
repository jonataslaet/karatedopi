import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { RequestService } from './request.service';

export const AuthenticationGuard: CanActivateFn = () => {
  const requestService = inject(RequestService);
  return requestService.isLoggedIn;
}