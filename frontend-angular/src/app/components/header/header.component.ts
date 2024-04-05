import { Component, OnInit } from '@angular/core';
import { RouteItem } from 'src/app/common/route.item';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  menusByRole: RouteItem[] = [];
  
  constructor(
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.authenticationService.setCurrentUser();
    this.authenticationService.setCurrentMenusByRole();
  }

  logout(): void {
    this.authenticationService.logoutAndStartFromLanding();
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

  getFirstAndLastName(): string {
    const firstname = this.currentUserSignal().firstname;
    const lastname = this.currentUserSignal().lastname;
    const firstAndLastName = firstname.concat(' ').concat(lastname)
    return firstAndLastName;
  }

  hasPrivileges(uri: string): boolean {
    return this.authenticationService.hasPrivileges(uri);
  }
}
