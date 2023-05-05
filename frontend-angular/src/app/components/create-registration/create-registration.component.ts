import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RegistrationForm } from 'src/app/common/registration-form';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-create-registration',
  templateUrl: './create-registration.component.html',
  styleUrls: ['./create-registration.component.css']
})
export class CreateRegistrationComponent implements OnInit {
  registrationForm: RegistrationForm = {
    id: null,
    email: '',
    password: '',
    firstname: '',
    lastname: '',
    father: '',
    mother: '',
    hometown: '',
    birthday: null,
    cpf: '',
    rg: '',
    phone: ''
  };

  constructor(private registrationService: RegistrationService, 
    private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {}

  createRegistration() {
    this.registrationService.createRegistration(this.registrationForm).subscribe(() => {
      this.router.navigate(['/']);
    });
  }

  cancel(): void{
    this.router.navigate(['/']);
  }

}
