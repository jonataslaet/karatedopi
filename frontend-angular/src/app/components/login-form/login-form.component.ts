import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { endpoints } from 'src/app/common/app.endpoints';
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
export class LoginFormComponent implements OnInit {
  authenticationResponseSubscription!: Subscription;
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

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser()
      .subscribe({
        next: (response: AuthenticationResponse) => {
          this.authenticationService.currentUserSignal.set(response);
          this.router.navigate(['/home']);
        },
        error: () => {
          this.router.navigate(['/login']);
        }
      });
  }

  formGroup = this.formBuiilder.nonNullable.group({
    email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
    password: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
  });

  onSubmitLogin(): void {
    const credentialsDTO: CredentialsDTO = {
      email: this.formGroup.get('email').value,
      password: this.formGroup.get('password').value,
    };
    this.authenticationResponseSubscription = this.authenticationService
      .authenticate(credentialsDTO)
      .subscribe((data) => {
        this.authenticationResponse = data;
        if (this.authenticationResponse.accessToken) {
          let menusByRole: MenuItem[] = [];
          this.authenticationService.setAuthToken(this.authenticationResponse.accessToken);
          endpoints.menus.forEach((menu: MenuItem) => {
            if (this.checkAuthority(menu, data.authorities)) {
              menusByRole.push(menu);
            }
          });
          this.authenticationService.currentMenusByRole.set(menusByRole);
          this.authenticationService.currentUserSignal.set(this.authenticationResponse);
          this.router.navigate(['/home']);
        }
      });
  }

  checkAuthority(menu: MenuItem, authorities: string[]): boolean {
    if (menu.authorities.some((authority) => authorities.includes(authority))) {
      return true;
    }
    return false;
  }
}
