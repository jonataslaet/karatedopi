import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { jwtDecode } from 'jwt-decode';
import { endpoints } from '../common/app.endpoints';
import { RouteItem } from '../common/route-item';
import { AuthenticationService } from './authentication.service';

export const AuthenticationGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
) => {
  const currentEndpoint = `/${route.url[0].path}`;
  const authenticationService = inject(AuthenticationService);
  const snackBar = inject(MatSnackBar);

  const jwtHelper = inject(JwtHelperService);
  if (jwtHelper.isTokenExpired(authenticationService.getAuthToken())) {
    snackBar.open(
      'Sua sessão expirou. Por favor, faça login novamente.',
      '❌'
    );
    authenticationService.startFromLogin();
  }

  let isAllowedByRole: boolean = false;
  if (authenticationService.isLoggedIn) {
    const menusByRole: RouteItem[] = [];
    endpoints.routes.forEach((menu: RouteItem) => {
      if (menu.authorities.some((authority) => jwtDecode(authenticationService.authenticatedToken)['authorities'].includes(authority)) && menu.isMenu) {
        menusByRole.push(menu);
      }
    });
    authenticationService.currentMenusByRole.set(menusByRole);
    isAllowedByRole = authenticationService.currentMenusByRole() ?
    authenticationService.currentMenusByRole().some(item => item.path === currentEndpoint) : false;
    if (isAllowedByRole) {
      return true;
    }
    snackBar.open('Acesso negado.','❌');
    inject(Router).navigate(['/home']);
    return false;
  }

  return authenticationService.isLoggedIn ? true : inject(Router).navigate(['/login']);
}