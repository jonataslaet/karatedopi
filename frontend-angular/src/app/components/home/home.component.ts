import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {

  authenticationResponse: AuthenticationResponse = {
    firstname: '',
    lastname: '',
    email: '',
    accessToken: '',
    roles: []
  };

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser().subscribe({
      next: (response: AuthenticationResponse) => {
        this.authenticationResponse = response;
        this.authenticationService.currentUserSignal.set(response);
      },
      error: () => {
        localStorage.setItem('auth_token', '');
        this.authenticationService.currentUserSignal.set(null);
        this.authenticationResponse = {
          firstname: '',
          lastname: '',
          email: '',
          accessToken: '',
          roles: []
        }
        this.router.navigate(['/login']);
      },
    });
  }

}
