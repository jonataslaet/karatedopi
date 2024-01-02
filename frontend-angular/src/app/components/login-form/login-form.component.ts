import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { CredentialsDTO } from 'src/app/common/credentials-dto';
import { MenuItem } from 'src/app/common/menu-item';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {
  authenticationResponse: AuthenticationResponse = {
    id: null,
    firstname: '',
    lastname: '',
    email: '',
    accessToken: '',
    authorities: []
  };

  constructor(
    private formBuiilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {}

  formGroup = this.formBuiilder.nonNullable.group({
    email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
    password: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
  });

  onSubmitLogin(): void {
    const credentialsDTO: CredentialsDTO = {
      email: this.formGroup.get('email').value,
      password: this.formGroup.get('password').value,
    };
    this.authenticationService.authenticate(credentialsDTO).subscribe({
      next: (response: AuthenticationResponse) => {
        this.authenticationResponse = response;
        this.authenticationService.setAuthToken(this.authenticationResponse.accessToken);
        this.authenticationService.currentUserSignal.set(this.authenticationResponse);
        this.router.navigate(['/home']);
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

  checkAuthority(menu: MenuItem, authorities: string[]): boolean {
    return menu.authorities.some((authority) => authorities.includes(authority));
  }
}
