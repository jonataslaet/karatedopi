import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationResponse } from 'src/app/common/authentication.response';
import { PasswordResetDTO } from 'src/app/common/password.reset.dto';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-renew-password',
  templateUrl: './renew-password.component.html',
  styleUrls: ['./renew-password.component.css']
})
export class RenewPasswordComponent implements OnInit{
  formGroup: FormGroup;
  token: string = '';
  authenticationResponse: AuthenticationResponse = {
    id: null,
    firstname: '',
    lastname: '',
    email: '',
    accessToken: '',
    authorities: []
  };

  constructor(
    private toastrService: ToastrService,
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    
    this.formGroup = this.formBuilder.group({
      newPassword: new FormControl(''),
      newPasswordConfirmation: new FormControl(''),
    });

    this.activatedRoute.paramMap.subscribe((param) => {
      this.token = param.get('token');
    });

  }

  onSubmitNewPassword(): void {
    const passwordReset: PasswordResetDTO = {
      newPassword: this.formGroup.get('newPassword').value,
      newPasswordConfirmation: this.formGroup.get('newPasswordConfirmation').value,
    };
    this.authenticationService.renewPassword(this.token, passwordReset).subscribe({
      next: () => {
        this.authenticationService.logoutAndStartFromLanding();
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
        this.authenticationService.logoutAndStartFromLanding();
      }
    });
  }

  cancel(): void{
    this.authenticationService.logoutAndStartFromLanding();
  }

}
