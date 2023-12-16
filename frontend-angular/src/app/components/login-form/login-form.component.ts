import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { CredentialsDTO } from 'src/app/common/credentials-dto';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {

  login: string = '';
  password: string = '';

  authenticationResponse: AuthenticationResponse = {
    email: '',
    accessToken: '',
  };

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  onSubmitLogin(): void {
    const credentialsDTO: CredentialsDTO = {
      email: this.login,
      password: this.password,
    };
    this.authenticationService
      .authenticate(credentialsDTO)
      .subscribe((data) => {
        this.authenticationResponse = data;
        if (this.authenticationResponse.accessToken) {
          this.authenticationService.setAuthToken(this.authenticationResponse.accessToken);
        }
      });
  }

  navigateToRegistration() {
    this.router.navigate(['/registration']);
  }
}
