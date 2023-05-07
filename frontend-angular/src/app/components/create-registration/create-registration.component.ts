import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { City } from 'src/app/common/city';
import { RegistrationForm } from 'src/app/common/registration-form';
import { State } from 'src/app/common/state';
import { StatesReadResponse } from 'src/app/common/states-read-response';
import { CityService } from 'src/app/services/city-service';
import { RegistrationService } from 'src/app/services/registration.service';
import { StateService } from 'src/app/services/state-service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-create-registration',
  templateUrl: './create-registration.component.html',
  styleUrls: ['./create-registration.component.css']
})
export class CreateRegistrationComponent implements OnInit {
  hide = true;
  states: State[] = [];
  addressCities: City[] = [];
  registrationForm: RegistrationForm = {
    id: null,
    email: '',
    password: '',
    fullname: '',
    father: '',
    mother: '',
    zipCode: '',
    street: '',
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

  registrationFormGroup = new FormGroup({
    user: new FormGroup({
      email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
      password: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
    }),
    personal: new FormGroup({
      fullname: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      father: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      mother: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      bloodType: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      birthday: new FormControl('', [Validators.required]),
      cpf: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      rg: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      phone: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
    }),
    address: new FormGroup({
      street: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
      number: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
      neighbourhood: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
      city: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
      state: new FormControl('', [Validators.required]),
      zipCode: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
    })
  });

  constructor(
    private formBuilder: FormBuilder, private registrationService: RegistrationService, 
    private router: Router, private activatedRoute: ActivatedRoute, 
    private cityService: CityService, private stateService: StateService) {}

  ngOnInit(): void {
    console.log('entrou em ngOnInit');
    this.stateService.getStates()
    .subscribe({
      next: (response: State[]) => {
        this.states = response
      },
      error: err => {
        alert(`There was an error: ${err.message}`);
      }
    })
  }

  get userEmail() { return this.registrationFormGroup.get('user.email');}
  get userPassword() { return this.registrationFormGroup.get('user.password');}

  get personalFullname() { return this.registrationFormGroup.get('personal.fullname');}
  get personalFather() { return this.registrationFormGroup.get('personal.father');}
  get personalMother() { return this.registrationFormGroup.get('personal.mother');}
  get personalBloodType() { return this.registrationFormGroup.get('personal.bloodType');}
  get personalBirthday() { return this.registrationFormGroup.get('personal.birthday');}
  get personalCPF() { return this.registrationFormGroup.get('personal.cpf');}
  get personalRG() { return this.registrationFormGroup.get('personal.rg');}
  get personalPhone() { return this.registrationFormGroup.get('personal.phone');}

  get addressZipCode() { return this.registrationFormGroup.get('address.zipCode');}
  get addressStreet() { return this.registrationFormGroup.get('address.street');}
  get addressNumber() { return this.registrationFormGroup.get('address.number');}
  get addressNeighbourhood() { return this.registrationFormGroup.get('address.neighbourhood');}
  get addressCity() { return this.registrationFormGroup.get('address.city');}
  get addressState() { return this.registrationFormGroup.get('address.state');}
  
  createRegistration() {

    if (this.registrationFormGroup.invalid) {
      this.registrationFormGroup.markAllAsTouched();
      return;
    }

    this.registrationService.createRegistration(this.registrationForm).subscribe(() => {
      this.router.navigate(['/']);
    });
    
  }

  cancel(): void{
    this.router.navigate(['/']);
  }

  getCities(formGroupName: string) {
    const formGroup = this.registrationFormGroup.get(formGroupName);
    console.log('form = ' + formGroupName);
    const stateCode = formGroup?.value.state.stateAbbreviation as string;
    console.log('stateCode = ' + stateCode);
    this.cityService.getCities(stateCode)
    .subscribe({
      next: (response: City[]) => {
        console.log(response);
        this.addressCities = response
      },
      error: err => {
        alert(`There was an error: ${err.message}`);
      }
    })
    ;
  }
}
