import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService, private router: Router) {}

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser()
      .subscribe({
        next: (response: AuthenticationResponse) => {
          this.authenticationService.currentUserSignal.set(response);
        },
        error: () => {
          this.logout();
        }
      });
  }

  logout(): void {
    localStorage.setItem('auth_token', '');
    this.authenticationService.currentUserSignal.set(null);
    this.router.navigate(['/login']);
  }

  currentUserSignal() {
    return this.authenticationService.currentUserSignal();
  }

}
