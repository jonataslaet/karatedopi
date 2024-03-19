import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CityDto } from 'src/app/common/city.dto';
import { RegistrationFormInputDto } from 'src/app/common/registration.form.input.dto';
import { StateDto } from 'src/app/common/state.dto';
import { AssociationService } from 'src/app/services/association.service';
import { CityService } from 'src/app/services/city.service';
import { RegistrationService } from 'src/app/services/registration.service';
import { StateService } from 'src/app/services/state.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-registration-form-create',
  templateUrl: './registration-form-create.component.html',
  styleUrls: ['./registration-form-create.component.css']
})
export class RegistrationFormCreateComponent implements OnInit {
  registrationFormGroup: FormGroup;
  hide = true;
  associationAbbreviations: string[] = [];
  addressStates: StateDto[] = [];
  addressCities: CityDto[] = [];
  bloodTipies: string[] = ["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"];

  registrationFormDto: RegistrationFormInputDto = {
    user: {
      firstname: '',
      lastname: '',
      email: '',
      password: ''
    },
    profile: {
      fullname: '',
      father: '',
      mother: '',
      bloodType: '',
      birthday: null,
      itin: '',
      nid: '',
      associationAbbreviation: null,
      phoneNumbers: []
    },
    address: {
      id: null,
      street: '',
      number: null,
      zipCode: '',
      neighbourhood: '',
      city: {
        id: null,
        name: '',
        state: {
          id: null,
          name: '',
          stateAbbreviation: null
        }
      }
    }
  };

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private registrationService: RegistrationService,
    private router: Router, private cityService: CityService,
    private stateService: StateService,
    private associationService: AssociationService
  ) {}

  ngOnInit(): void {
    this.registrationFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        email: new FormControl('', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
        password: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
        firstname: new FormControl('', []),
        lastname: new FormControl('', []),
      }),
      profile: this.formBuilder.group({
        fullname: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        father: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        mother: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        bloodType: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        birthday: new FormControl('', [Validators.required]),
        itin: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        nid: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        firstPhone: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        secondPhone: new FormControl('', []),
        thirdPhone: new FormControl('', []),
        associationAbbreviation: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      }),
      address: this.formBuilder.group({
        street: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
        number: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
        neighbourhood: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
        city: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
        state: new FormControl('', [Validators.required]),
        zipCode: new FormControl('', [Validators.required, Validators.minLength(2), KaratedopiValidators.notOnlyWhitespace]),
      })
    });
    this.stateService.getStates()
    .subscribe({
      next: (response: StateDto[]) => {
        this.addressStates = response
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
    this.associationService.getAllAssociationAbbreviations()
    .subscribe({
      next: (response: string[]) => {
        this.associationAbbreviations = response
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  get userEmail() { return this.registrationFormGroup.get('user.email');}
  get userPassword() { return this.registrationFormGroup.get('user.password');}
  get userFirstname() { return this.registrationFormGroup.get('user.firstname');}
  get userLastname() { return this.registrationFormGroup.get('user.lastname');}

  get profileFullname() { return this.registrationFormGroup.get('profile.fullname');}
  get profileFather() { return this.registrationFormGroup.get('profile.father');}
  get profileMother() { return this.registrationFormGroup.get('profile.mother');}
  get profileBloodType() { return this.registrationFormGroup.get('profile.bloodType');}
  get profileBirthday() { return this.registrationFormGroup.get('profile.birthday');}
  get profileITIN() { return this.registrationFormGroup.get('profile.itin');}
  get profileAssociationAbbreviation() { return this.registrationFormGroup.get('profile.associationAbbreviation');}
  get profileNID() { return this.registrationFormGroup.get('profile.nid');}
  get profileFirstPhone() { return this.registrationFormGroup.get('profile.firstPhone');}
  get profileSecondPhone() { return this.registrationFormGroup.get('profile.secondPhone');}
  get profileThirdPhone() { return this.registrationFormGroup.get('profile.thirdPhone');}
  get profileCurrentBelt() { return this.registrationFormGroup.get('profile.currentBelt');}

  get addressStreet() { return this.registrationFormGroup.get('address.street');}
  get addressNumber() { return this.registrationFormGroup.get('address.number');}
  get addressZipCode() { return this.registrationFormGroup.get('address.zipCode');}
  get addressNeighbourhood() { return this.registrationFormGroup.get('address.neighbourhood');}
  get addressCity() { return this.registrationFormGroup.get('address.city');}
  get addressState() { return this.registrationFormGroup.get('address.state');}
  
  createRegistration() {
    this.fillRegistrationForm();
    this.registrationService.createRegistration(this.registrationFormDto)
    .subscribe({
      next: (response: RegistrationFormInputDto) => {
        this.registrationFormDto = response;
        this.toastrService.success('Cadastro efetuado com sucesso.', 'Sucesso');
        this.router.navigate(['/login']);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  fillRegistrationForm(): void {
    this.registrationFormDto.user.email = this.userEmail.value;
    this.registrationFormDto.user.password = this.userPassword.value;
    this.registrationFormDto.user.firstname = this.userFirstname.value;
    this.registrationFormDto.user.lastname = this.userLastname.value;
    
    this.registrationFormDto.profile.fullname = this.profileFullname.value;
    this.registrationFormDto.profile.father = this.profileFather.value;
    this.registrationFormDto.profile.mother = this.profileMother.value;
    this.registrationFormDto.profile.bloodType = this.profileBloodType.value;
    this.addValidPhone(this.profileFirstPhone.value, this.profileSecondPhone.value, this.profileThirdPhone.value);
    this.registrationFormDto.profile.itin = this.profileITIN.value;
    this.registrationFormDto.profile.associationAbbreviation = this.profileAssociationAbbreviation.value;
    this.registrationFormDto.profile.nid = this.profileNID.value;
    this.registrationFormDto.profile.birthday = this.getFormattedDate(this.profileBirthday.value);
    this.registrationFormDto.address.zipCode = this.addressZipCode.value;
    this.registrationFormDto.address.street = this.addressStreet.value;
    this.registrationFormDto.address.number = this.addressNumber.value;
    this.registrationFormDto.address.neighbourhood = this.addressNeighbourhood.value;
    this.registrationFormDto.address.city = this.addressCity.value;
    
  }

  getFormattedDate(dateString: string): Date | null {
    const day = parseInt(dateString.substring(8, 10), 10);
    const month = parseInt(dateString.substring(5, 7), 10) - 1;
    const year = parseInt(dateString.substring(0, 4), 10);
    const date = new Date();
    date.setUTCFullYear(year);
    date.setUTCMonth(month);
    date.setUTCDate(day);
  
    if (!isNaN(date.getTime())) {
      return date;
    }
    return null;
  }

  getDateFormat(birthday: string): Date | null {
    const match = birthday.match(/^(\d{4})-(\d{2})-(\d{2})$/);
    if (match) {
      const year = parseInt(match[1], 10);
      const month = parseInt(match[2], 10) - 1;
      const day = parseInt(match[3], 10);
      const date = new Date(year, month, day);
      if (!isNaN(date.getTime())) {
        return date;
      }
    }
    return null;
  }

  cancel(): void{
    this.router.navigate(['/']);
  }

  fillCities(formGroupName: string) {
    const formGroup = this.registrationFormGroup.get(formGroupName);
    const stateAbbreviation = formGroup?.value.state.stateAbbreviation as string;
    this.cityService.getCities(stateAbbreviation)
    .subscribe({
      next: (response: CityDto[]) => {
        this.addressCities = response
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  addValidPhone(phone1: string, phone2: string, phone3: string): void {
    this.registrationFormDto.profile.phoneNumbers.push(phone1);
    if (this.isValidPhone(phone2)) {
      this.registrationFormDto.profile.phoneNumbers.push(phone2);
    }
    if (this.isValidPhone(phone3)) {
      this.registrationFormDto.profile.phoneNumbers.push(phone3);
    }
  }

  isValidPhone(phone: string) {
    return phone !== null && phone !== undefined && phone.trim() !== '';
  }
}
