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
  menusByRole: MenuItem[] = [];

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser().subscribe({
      next: (response: AuthenticationResponse) => {
        if (this.menusByRole.length == 0) {
          endpoints.menus.forEach((menu: MenuItem) => {
            if (this.checkAuthority(menu, response.authorities)) {
              this.menusByRole.push(menu);
            }
          });
        }
        this.authenticationService.currentUserSignal.set(response);
      },
      error: () => {
        this.logout();
      },
    });
  }

  logout(): void {
    this.menusByRole = [];
    localStorage.setItem('auth_token', '');
    this.authenticationService.currentUserSignal.set(null);
    this.router.navigate(['/login']);
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
