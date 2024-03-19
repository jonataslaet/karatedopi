import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { jwtDecode } from 'jwt-decode';
import { ToastrService } from 'ngx-toastr';
import { endpoints } from '../common/app.endpoints';
import { RouteItem } from '../common/route.item';
import { AuthenticationService } from './authentication.service';

export const AuthenticationGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
) => {
  const currentEndpoint = '/'+route.url.map(segment => segment.toString()).join('/');
  const authenticationService = inject(AuthenticationService);
  const toastrService = inject(ToastrService);
  const jwtHelper = inject(JwtHelperService);

  if (jwtHelper.isTokenExpired(authenticationService.getAuthToken())) {
    toastrService.error('Sua sessão expirou. Por favor, faça login novamente.', 'Erro');
    authenticationService.startFromLogin();
  }

  let isAllowedByRole: boolean = false;
  if (authenticationService.isLoggedIn) {
    const menusByRole: RouteItem[] = [];
    const endpointsByRole: RouteItem[] = [];
    endpoints.routes.forEach((menu: RouteItem) => {
      if (
        menu.authorities.some((authority) =>
          jwtDecode(authenticationService.authenticatedToken)['authorities'].includes(authority))
      ) {
        if (menu.isMenu) {
          menusByRole.push(menu);
        }
        endpointsByRole.push(menu);
      }
    });
    authenticationService.currentMenusByRole.set(menusByRole);

    const isAllowedByRole = endpointsByRole ?
    endpointsByRole.some((item: RouteItem) => {
      const regex = new RegExp(`^${item.path.replace(/:\w+/g, '[^/]+')}$`);
      return regex.test(currentEndpoint);
    }) : false;

    if (isAllowedByRole || (route.url[2] !== undefined && (authenticationService.currentUserSignal().id === Number.parseInt(route.url[2].path)))) {
      return true;
    }
    toastrService.error('O acesso a esse recurso está restrito.', 'Acesso restrito');
    inject(Router).navigate(['/home']);
    return false;
  }

  return authenticationService.isLoggedIn
    ? true
    : inject(Router).navigate(['/login']);
};
