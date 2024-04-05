import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
// import { ToastrService } from 'ngx-toastr/toastr/toastr.service'; it goes 
import { ToastrService } from 'ngx-toastr';
import { EmailDTO } from 'src/app/common/email.dto';
import { RouteItem } from 'src/app/common/route.item';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-recovery-token',
  templateUrl: './recovery-token.component.html',
  styleUrls: ['./recovery-token.component.css']
})
export class RecoveryTokenComponent {
  responseMessage: string = '';

  constructor(
    private toastrService: ToastrService,
    private formBuiilder: FormBuilder,
    private authenticationService: AuthenticationService
  ) {}

  formGroup = this.formBuiilder.nonNullable.group({
    email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
    password: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
  });

  onSubmitRequest(): void {
    const emailDTO: EmailDTO = {
      address: this.formGroup.get('email').value
    };
    
    this.authenticationService.recoveryToken(emailDTO).subscribe({
      next: (response: string) => {
        this.responseMessage = response;
        this.requestAndStartFromLogin()
      },
      error: () => {
        this.requestAndStartFromLogin()
      },
    });
  }

  checkAuthority(menu: RouteItem, authorities: string[]): boolean {
    return menu.authorities.some((authority) => authorities.includes(authority));
  }

  cancel(): void{
    this.authenticationService.logoutAndStartFromLanding();
  }

  requestAndStartFromLogin(): void {
    this.toastrService.success(this.responseMessage, 'Solicitação de recuperação');
    this.authenticationService.logoutAndStartFromLanding();
  }
}
