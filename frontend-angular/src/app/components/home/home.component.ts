import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationResponse } from 'src/app/common/authentication.response';
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
    private authenticationService: AuthenticationService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser().subscribe({
      next: (response: AuthenticationResponse) => {
        this.authenticationResponse = response;
        this.authenticationService.currentUserSignal.set(response);
      },
      error: (err) => {
        this.authenticationResponse = {
          id: null,
          firstname: '',
          lastname: '',
          email: '',
          accessToken: '',
          authorities: []
        }
        this.authenticationService.logoutAndStartFromLanding();
        this.toastrService.error(err.error.message, err.error.error);
      },
    });
  }

}
