import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { endpoints } from '../common/app.endpoints';
import { MenuItem } from '../common/menu-item';
import { AuthenticationService } from './authentication.service';

export const AuthenticationGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
) => {
  const currentEndpoint = `/${route.url[0].path}`;
  const authenticationService = inject(AuthenticationService);
  let isAllowedByRole: boolean = false;
  if (authenticationService.isLoggedIn) {
    const menusByRole: MenuItem[] = [];
    endpoints.menus.forEach((menu: MenuItem) => {
      if (menu.authorities.some((authority) => jwtDecode(authenticationService.authenticatedToken)['authorities'].includes(authority))) {
        menusByRole.push(menu);
      }
    });
    authenticationService.currentMenusByRole.set(menusByRole);
    isAllowedByRole = authenticationService.currentMenusByRole() ?
      authenticationService.currentMenusByRole().some(item => item.path === currentEndpoint) : false;
    return isAllowedByRole ? true: inject(Router).navigate(['/home']);
  }

  return authenticationService.isLoggedIn ? true : inject(Router).navigate(['/login']);
}