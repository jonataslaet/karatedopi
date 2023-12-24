import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { endpoints } from 'src/app/common/app.endpoints';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { MenuItem } from 'src/app/common/menu-item';
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
        let menusByRole: MenuItem[] = [];
        endpoints.menus.forEach((menu: MenuItem) => {
          if (this.checkAuthority(menu, response.authorities)) {
            menusByRole.push(menu);
          }
        });
        this.authenticationService.currentMenusByRole.set(menusByRole);
        this.authenticationService.currentUserSignal.set(this.authenticationResponse);
      },
      error: () => {
        this.logout();
      },
    });
  }

  logout(): void {
    localStorage.setItem('auth_token', '');
    this.authenticationService.currentUserSignal.set(null);
    this.authenticationService.currentMenusByRole.set(null);
    this.router.navigate(['/login']);
  }

  currentMenusByRole() {
    return this.authenticationService.currentMenusByRole();
  }

  currentUserSignal() {
    return this.authenticationService.currentUserSignal();
  }

  checkAuthority(menu: MenuItem, authorities: string[]): boolean {
    if (menu.authorities.some((authority) => authorities.includes(authority))) {
      return true;
    }
    return false;
  }
}
