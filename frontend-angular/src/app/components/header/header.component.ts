import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { endpoints } from 'src/app/common/app.endpoints';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { RouteItem } from 'src/app/common/route-item';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {

  authenticationResponse: AuthenticationResponse = {
    id: null,
    firstname: '',
    lastname: '',
    email: '',
    accessToken: '',
    authorities: [],
  };

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser().subscribe({
      next: (response: AuthenticationResponse) => {
        this.authenticationService.currentUserSignal.set(response);
        let menusByRole: RouteItem[] = [];
        endpoints.routes.forEach((route: RouteItem) => {
          if (this.checkAuthority(route, response.authorities) && route.isMenu) {
            menusByRole.push(route);
          }
        });
        this.authenticationService.currentMenusByRole.set(menusByRole);
        this.authenticationService.currentUserSignal.set(this.authenticationResponse);
      },
      error: () => {
        this.authenticationService.startFromLogin();
      },
    });
  }

  logout(): void {
    this.authenticationService.startFromLogin();
  }

  currentMenusByRole() {
    return this.authenticationService.currentMenusByRole();
  }

  currentUserSignal() {
    return this.authenticationService.currentUserSignal();
  }

  checkAuthority(route: RouteItem, authorities: string[]): boolean {
    return route.authorities.some((authority) => authorities.includes(authority));
  }
}
