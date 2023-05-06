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
  hide = true;
  registrationForm: RegistrationForm = {
    id: null,
    email: '',
    password: '',
    fullname: '',
    father: '',
    mother: '',
    zipCode: '',
    address: '',
    number: '',
    neighbourhood: '',
    city: '',
    state: '',
    bloodType: '',
    phone: '',
    birthday: null,
    cpf: '',
    rg: ''
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
