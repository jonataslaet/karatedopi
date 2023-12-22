import { Component, OnInit } from '@angular/core';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {

  authenticationResponse: AuthenticationResponse = {
    id: null,
    firstname: '',
    lastname: '',
    email: '',
    accessToken: '',
    authorities: []
  };

  constructor(
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    console.log(localStorage.getItem('auth_token'));
    this.authenticationService.getAuthenticatedUser().subscribe({
      next: (response: AuthenticationResponse) => {
        this.authenticationResponse = response;
        this.authenticationService.currentUserSignal.set(response);
      },
      error: () => {
        this.authenticationResponse = {
          id: null,
          firstname: '',
          lastname: '',
          email: '',
          accessToken: '',
          authorities: []
        }
        this.authenticationService.startFromLogin();
      },
    });
  }

}
