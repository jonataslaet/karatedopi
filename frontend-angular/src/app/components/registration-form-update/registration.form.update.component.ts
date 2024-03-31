import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { ToastrService } from 'ngx-toastr';
import { CityDto } from 'src/app/common/city.dto';
import { RegistrationFormInputDto } from 'src/app/common/registration.form.input.dto';
import { RegistratrionFormOutputDto } from 'src/app/common/registratrion.form.output.dto';
import { StateDto } from 'src/app/common/state.dto';
import { AssociationService } from 'src/app/services/association.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CityService } from 'src/app/services/city.service';
import { RegistrationService } from 'src/app/services/registration.service';
import { StateService } from 'src/app/services/state.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-registration.form.update',
  templateUrl: './registration.form.update.component.html',
  styleUrls: ['./registration.form.update.component.css']
})
export class RegistrationFormUpdateComponent implements OnInit {
  selectedPhoto: File | null = null;
  registrationFormGroup: FormGroup;
  gotDefaultState: boolean = false;
  gotDefaultAssociationAbbreviation: boolean = false;
  associationAbbreviations: string[] = [];
  hide = true;
  userId: number = null;
  addressStates: StateDto[] = [];
  addressCities: CityDto[] = [];

  bloodTipies: string[] = ["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"];

  registrationFormInputDto: RegistrationFormInputDto = {
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
      nidPhotoUrl: '',
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
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cityService: CityService,
    private stateService: StateService,
    private associationService: AssociationService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.gotDefaultState = false;
    this.gotDefaultAssociationAbbreviation = false;
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
        firstPhone: new FormControl('', []),
        secondPhone: new FormControl('', []),
        thirdPhone: new FormControl('', []),
        associationAbbreviation: new FormControl('')
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

    this.activatedRoute.paramMap.subscribe((param) => {
      this.userId = Number(param.get('id'));
      this.getRegistrationFormByUserId(this.userId);
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
  get profileNID() { return this.registrationFormGroup.get('profile.nid');}
  get profileFirstPhone() { return this.registrationFormGroup.get('profile.firstPhone');}
  get profileSecondPhone() { return this.registrationFormGroup.get('profile.secondPhone');}
  get profileThirdPhone() { return this.registrationFormGroup.get('profile.thirdPhone');}
  get profileCurrentBelt() { return this.registrationFormGroup.get('profile.currentBelt');}
  get profileAssociationAbbreviation() { return this.registrationFormGroup.get('profile.associationAbbreviation');}

  get addressStreet() { return this.registrationFormGroup.get('address.street');}
  get addressNumber() { return this.registrationFormGroup.get('address.number');}
  get addressZipCode() { return this.registrationFormGroup.get('address.zipCode');}
  get addressNeighbourhood() { return this.registrationFormGroup.get('address.neighbourhood');}
  get addressCity() { return this.registrationFormGroup.get('address.city');}
  get addressState() { return this.registrationFormGroup.get('address.state');}

  getRegistrationFormByUserId(userId: number) {
    this.registrationService.readRegistrationFormByUserId(userId)
      .subscribe({
        next: (data) => {
          this.fillRegistrationFormInputFromRegistrationFormOutput(data);
          this.fillFormGroup(data);
        },
        error: err => {
          this.toastrService.error(err.error.message, err.error.error);
        }
    });
  }
  
  private fillFormGroup(data: RegistratrionFormOutputDto) {
    this.userEmail.setValue(data.user.email);
    this.userFirstname.setValue(data.user.firstname);
    this.userLastname.setValue(data.user.lastname);

    this.addressStreet.setValue(data.address.street);
    this.addressNumber.setValue(data.address.number);
    this.addressZipCode.setValue(data.address.city.name);
    this.addressNeighbourhood.setValue(data.address.neighbourhood);
    this.addressCity.setValue(data.address.city.name);
    this.addressState.setValue(data.address.city.state.name);

    this.profileFullname.setValue(data.profile.fullname);
    this.profileFather.setValue(data.profile.father);
    this.profileMother.setValue(data.profile.mother);
    this.profileBloodType.setValue(data.profile.bloodType);
    this.profileBirthday.setValue(this.formatDateToYYYYMMDD(new Date(data.profile.birthday)));
    this.profileITIN.setValue(data.profile.itin);
    this.profileAssociationAbbreviation.setValue(data.profile.associationAbbreviation);

    this.profileNID.setValue(data.profile.nid);
    for (let i = 0; i < data.profile.phoneNumbers.length; i++) {
      this.setPhone(i, data.profile.phoneNumbers[i]);
    }
  }

  updateRegistrationForm() {
    this.fillRegistrationForm();

    this.registrationService.uploadImage(this.selectedPhoto)
    .subscribe({
      next: (response) => {
        if (response !== null && response.headers !== null && response.headers.has('Location')) {
          this.registrationFormInputDto.profile.nidPhotoUrl = response.headers.get('Location');
          this.toastrService.success('Upload da foto realizado com sucesso.', 'Sucesso');
        } else {
          this.toastrService.error('URI não encontrada no cabeçalho da resposta.', 'Erro');
        }
      },
      error: err => {
        console.error('Erro ao enviar imagem:', err.error.error);
        this.toastrService.error(err.error.message, err.error.error);
      }
    });

    if (this.userId !== jwtDecode(this.authenticationService.authenticatedToken)['id']) {
      this.registrationService.updateRegistrationFormByUserId(this.userId, this.registrationFormInputDto)
      .subscribe({
        next: () => {
          this.router.navigate(['/fichas']);
        },
        error: err => {
          this.toastrService.error(err.error.message, err.error.error);
        }
      });
    } else {
      this.registrationService.updateRegistrationForm(this.registrationFormInputDto)
        .subscribe({
          next: () => {
            this.router.navigate(['/fichas']);
          },
          error: err => {
            this.toastrService.error(err.error.message, err.error.error);
          }
      });
    }
    
  }

  onPhotoSelected(event: any): void {
    this.selectedPhoto = event.target.files[0] as File;
    console.log(`this.selectedPhoto = ${this.selectedPhoto}`);
  }

  fillRegistrationForm() {
    this.registrationFormInputDto.user.email = this.userEmail.value;
    this.registrationFormInputDto.user.password = this.userPassword.value;
    this.registrationFormInputDto.user.firstname = this.userFirstname.value;
    this.registrationFormInputDto.user.lastname = this.userLastname.value;

    this.registrationFormInputDto.profile.fullname = this.profileFullname.value;
    this.registrationFormInputDto.profile.father = this.profileFather.value;
    this.registrationFormInputDto.profile.mother = this.profileMother.value;
    this.registrationFormInputDto.profile.bloodType = this.profileBloodType.value;
    this.addValidPhone(this.profileFirstPhone.value, this.profileSecondPhone.value, this.profileThirdPhone.value);
    this.registrationFormInputDto.profile.itin = this.profileITIN.value;
    this.registrationFormInputDto.profile.nid = this.profileNID.value;
    this.registrationFormInputDto.profile.birthday = this.getFormattedDate(this.profileBirthday.value);
    this.registrationFormInputDto.profile.associationAbbreviation = this.profileAssociationAbbreviation.value;

    this.registrationFormInputDto.address.zipCode = this.addressZipCode.value;
    this.registrationFormInputDto.address.street = this.addressStreet.value;
    this.registrationFormInputDto.address.number = this.addressNumber.value;
    this.registrationFormInputDto.address.neighbourhood = this.addressNeighbourhood.value;
    this.registrationFormInputDto.address.city.name = this.addressCity.value;
    this.registrationFormInputDto.address.city.state.name = this.addressState.value;
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

  formatDateToYYYYMMDD(date: Date): string {
    const year = date.getUTCFullYear();
    const month = (date.getUTCMonth() + 1).toString().padStart(2, '0');
    const day = date.getUTCDate().toString().padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;
    return formattedDate;
  }

  cancel(): void{
    this.router.navigate(['/fichas']);
  }

  updateAddressRegistrationFormGroup() {
    this.registrationFormGroup.patchValue({
      address: {
        street: this.registrationFormInputDto.address.street,
        number: this.registrationFormInputDto.address.number,
        zipCode: this.registrationFormInputDto.address.zipCode,
        neighbourhood: this.registrationFormInputDto.address.neighbourhood,
        state: this.registrationFormInputDto.address.city.state.name,
        city: this.registrationFormInputDto.address.city.name
      }
    });
  }


  fillCities() {
    this.registrationFormInputDto.address.city.state = this.getStateByName(this.addressState.value);
    const stateAbbreviation = this.registrationFormInputDto.address.city.state.stateAbbreviation;
    this.updateAddressRegistrationFormGroup();
    this.cityService.getCities(stateAbbreviation as string)
    .subscribe({
      next: (response: CityDto[]) => {
        this.addressCities = response
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }


  updatePhoneNumber(editPhone: HTMLInputElement, numberId: number) {
    this.registrationFormInputDto.profile.phoneNumbers[numberId] = editPhone.value;
    this.setPhone(numberId, editPhone.value);
  }

  private setPhone(numberId: number, editPhone: string) {
    if (numberId == 0) {
      this.profileFirstPhone.setValue(editPhone);
    } else if (numberId == 1) {
      this.profileSecondPhone.setValue(editPhone);
    } else if (numberId == 2) {
      this.profileThirdPhone.setValue(editPhone);
    }
  }

  addValidPhone(phone1: string, phone2: string, phone3: string): void {
    if (this.isValidPhone(phone1)) {
      this.registrationFormInputDto.profile.phoneNumbers.push(phone1);
    }
    if (this.isValidPhone(phone2)) {
      this.registrationFormInputDto.profile.phoneNumbers.push(phone2);
    }
    if (this.isValidPhone(phone3)) {
      this.registrationFormInputDto.profile.phoneNumbers.push(phone3);
    }
  }

  isValidPhone(phone: string) {
    return phone !== null && phone !== undefined && phone !== '';
  }

  getSelectedStateOption(state: StateDto) {
    if (this.addressState.value === state.name && !this.gotDefaultState) {
      this.gotDefaultState = true;
      this.fillCities();
    }
    return this.addressState.value === state.name;
  }

  getSelectedAssociationAbbreviationOption(abbreviation: string): boolean {
    return this.profileAssociationAbbreviation.value === abbreviation;
  }

  getSelectedCityOption(city: CityDto) {
    return this.addressCity.value === city.name;
  }

  getStateByName(stateName: string): StateDto | undefined {
    return this.addressStates.find((state) => state.name === stateName);
  }

  fillRegistrationFormInputFromRegistrationFormOutput(response: RegistratrionFormOutputDto): void {
    this.registrationFormInputDto.user.email = response.user.email;
    this.registrationFormInputDto.user.firstname = response.user.firstname;
    this.registrationFormInputDto.user.lastname = response.user.lastname;

    this.registrationFormInputDto.address.id = response.address.id;
    this.registrationFormInputDto.address.city = response.address.city;
    this.registrationFormInputDto.address.neighbourhood = response.address.neighbourhood;
    this.registrationFormInputDto.address.number = response.address.number;
    this.registrationFormInputDto.address.zipCode = response.address.zipCode;
    this.registrationFormInputDto.address.street = response.address.street;
    this.registrationFormInputDto.profile.associationAbbreviation = response.profile.associationAbbreviation;

    this.registrationFormInputDto.profile.birthday = response.profile.birthday;
    this.registrationFormInputDto.profile.bloodType = response.profile.bloodType;
    this.registrationFormInputDto.profile.father = response.profile.father;
    this.registrationFormInputDto.profile.fullname = response.profile.fullname;
    this.registrationFormInputDto.profile.itin = response.profile.itin;
    this.registrationFormInputDto.profile.mother = response.profile.mother;
    this.registrationFormInputDto.profile.nid = response.profile.nid;
    this.registrationFormInputDto.profile.nidPhotoUrl = response.profile.nidPhotoUrl;
    this.registrationFormInputDto.profile.phoneNumbers = response.profile.phoneNumbers;
  }

}

