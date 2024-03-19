import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AssociationInputDto } from 'src/app/common/association.input.dto';
import { AssociationOutputDto } from 'src/app/common/association.output.dto';
import { CityDto } from 'src/app/common/city.dto';
import { OrganizationStatusEnum } from 'src/app/common/organization.status.enum';
import { StateDto } from 'src/app/common/state.dto';
import { AssociationService } from 'src/app/services/association.service';
import { CityService } from 'src/app/services/city.service';
import { FederationService } from 'src/app/services/federation.service';
import { StateService } from 'src/app/services/state.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-association-update',
  templateUrl: './association-update.component.html',
  styleUrls: ['./association-update.component.css']
})
export class AssociationUpdateComponent implements OnInit {

  gotDefaultState: boolean = false;
  gotDefaultFederationAbbreviation: boolean = false;
  federationAbbreviations: string[] = [];
  associationInputGroup: FormGroup;
  associationId: number;
  hide = true;
  addressStates: StateDto[] = [];
  addressCities: CityDto[] = [];

  statuses = Object.keys(OrganizationStatusEnum) as OrganizationStatusEnum[];

  associationInputDto: AssociationInputDto = {
    businessName: '',
    tradeName: '',
    associationAbbreviation: '',
    federationAbbreviation: '',
    foundationDate: null,
    ein: '',
    email: '',
    presidentName: '',
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
    },
    phoneNumbers: []
  };

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private associationService: AssociationService,
    private federationService: FederationService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cityService: CityService,
    private stateService: StateService
  ) {}

  ngOnInit(): void {
    this.federationService.getAllFederationAbbreviations()
    .subscribe({
      next: (response: string[]) => {
        this.federationAbbreviations = response
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });

    this.associationInputGroup = this.formBuilder.group({
      information: this.formBuilder.group({
        businessName: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        tradeName: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        associationAbbreviation: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        federationAbbreviation: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        foundationDate: new FormControl('', [Validators.required]),
        ein: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        email: new FormControl('', [Validators.required, Validators.email]),
        firstPhone: new FormControl('', []),
        secondPhone: new FormControl('', []),
        thirdPhone: new FormControl('', []),
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
      this.associationId = Number(param.get('id'));
      this.getAssociationById(this.associationId);
    });

    this.stateService.getStates()
    .subscribe({
      next: (response: StateDto[]) => {
        this.addressStates = response
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    })
  }

  get associationBusinessName() { return this.associationInputGroup.get('information.businessName');}
  get associationTradeName() { return this.associationInputGroup.get('information.tradeName'); }
  get associationAbbreviation() { return this.associationInputGroup.get('information.associationAbbreviation'); }
  get federationAbbreviation() { return this.associationInputGroup.get('information.federationAbbreviation'); }
  get associationFoundationDate() { return this.associationInputGroup.get('information.foundationDate'); }
  get associationEin() { return this.associationInputGroup.get('information.ein'); }
  get associationEmail() { return this.associationInputGroup.get('information.email'); }
  get associationFirstPhone() { return this.associationInputGroup.get('information.firstPhone');}
  get associationSecondPhone() { return this.associationInputGroup.get('information.secondPhone');}
  get associationThirdPhone() { return this.associationInputGroup.get('information.thirdPhone');}

  get addressZipCode() { return this.associationInputGroup.get('address.zipCode');}
  get addressStreet() { return this.associationInputGroup.get('address.street');}
  get addressNumber() { return this.associationInputGroup.get('address.number');}
  get addressNeighbourhood() { return this.associationInputGroup.get('address.neighbourhood');}
  get addressCity() { return this.associationInputGroup.get('address.city');}
  get addressState() { return this.associationInputGroup.get('address.state');}
  
  updateAssociation() {
    this.fillAssociationInput();
    this.associationService.updateAssociation(this.associationId, this.associationInputDto).subscribe({
      next: ()=> {
        this.toastrService.success('Atualização efetuada com sucesso.', 'Sucesso');
        this.router.navigate(['/federacoes']);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  getAssociationById(id: number) {
    this.associationService.getAssociationById(id).subscribe({
      next: (data: AssociationOutputDto) => {
        this.associationInputDto = data;

        this.associationBusinessName.setValue(data.businessName);
        this.associationTradeName.setValue(data.tradeName);
        this.associationAbbreviation.setValue(data.associationAbbreviation);
        this.federationAbbreviation.setValue(data.federationAbbreviation);
        this.associationFoundationDate.setValue(this.formatDateToYYYYMMDD(new Date(data.foundationDate)));
        this.associationEin.setValue(data.ein);
        this.associationEmail.setValue(data.email);
        for (let i = 0; i < data.phoneNumbers.length; i++) {
          this.setPhone(i, data.phoneNumbers[i]);
        }
  
        this.addressStreet.setValue(data.address.street);
        this.addressNumber.setValue(data.address.number);
        this.addressZipCode.setValue(data.address.city.name);
        this.addressNeighbourhood.setValue(data.address.neighbourhood);
        this.addressCity.setValue(data.address.city.name);
        this.addressState.setValue(data.address.city.state.name);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  formatDateToYYYYMMDD(date: Date): string {
    const year = date.getUTCFullYear();
    const month = (date.getUTCMonth() + 1).toString().padStart(2, '0');
    const day = date.getUTCDate().toString().padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;
    return formattedDate;
  }

  fillAssociationInput(): void {
    this.associationInputDto.businessName = this.associationBusinessName.value;
    this.associationInputDto.foundationDate = this.getFormattedDate(this.associationFoundationDate.value);
    this.associationInputDto.tradeName = this.associationTradeName.value;
    this.associationInputDto.associationAbbreviation = this.associationAbbreviation.value;
    this.associationInputDto.federationAbbreviation = this.federationAbbreviation.value;
    this.associationInputDto.ein = this.associationEin.value;
    this.associationInputDto.email = this.associationEmail.value;
    this.addValidPhone(this.associationFirstPhone.value, this.associationSecondPhone.value, this.associationThirdPhone.value);

    this.associationInputDto.address.zipCode = this.addressZipCode.value;
    this.associationInputDto.address.street = this.addressStreet.value;
    this.associationInputDto.address.number = this.addressNumber.value;
    this.associationInputDto.address.neighbourhood = this.addressNeighbourhood.value;
    this.associationInputDto.address.city.name = this.addressCity.value;
    this.associationInputDto.address.city.state.name = this.addressState.value;
  }

  addValidPhone(phone1: string, phone2: string, phone3: string): void {
    if (this.isValidPhone(phone1)) {
      this.associationInputDto.phoneNumbers.push(phone1);
    }
    if (this.isValidPhone(phone2)) {
      this.associationInputDto.phoneNumbers.push(phone2);
    }
    if (this.isValidPhone(phone3)) {
      this.associationInputDto.phoneNumbers.push(phone3);
    }
  }

  isValidPhone(phone: string) {
    return phone !== null && phone !== undefined && phone !== '';
  }

  getFormattedDate(dateString: string): Date | null {
    const day = parseInt(dateString.substring(8, 10), 10);
    const month = parseInt(dateString.substring(5, 7), 10) - 1;
    const year = parseInt(dateString.substring(0, 4), 10);
    const date = new Date();
    date.setUTCFullYear(year);
    date.setUTCMonth(month);
    date.setUTCDate(day);
    date.setUTCHours(0);
    date.setUTCMinutes(0);
  
    if (!isNaN(date.getTime())) {
      return date;
    }
    return null;
  }

  fillCities() {
    this.associationInputDto.address.city.state = this.getStateByName(this.addressState.value);
    const stateAbbreviation = this.associationInputDto.address.city.state.stateAbbreviation;
    this.updateAddressAssociationGroup();
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
    this.associationInputDto.phoneNumbers[numberId] = editPhone.value;
    this.setPhone(numberId, editPhone.value);
  }

  updateAddressAssociationGroup() {
    this.associationInputGroup.patchValue({
      address: {
        street: this.associationInputDto.address.street,
        number: this.associationInputDto.address.number,
        zipCode: this.associationInputDto.address.zipCode,
        neighbourhood: this.associationInputDto.address.neighbourhood,
        state: this.associationInputDto.address.city.state.name,
        city: this.associationInputDto.address.city.name
      }
    });
  }

  private setPhone(numberId: number, editPhone: string) {
    if (numberId == 0) {
      this.associationFirstPhone.setValue(editPhone);
    } else if (numberId == 1) {
      this.associationSecondPhone.setValue(editPhone);
    } else if (numberId == 2) {
      this.associationThirdPhone.setValue(editPhone);
    }
  }

  getStateByName(stateName: string): StateDto | undefined {
    return this.addressStates.find((state) => state.name === stateName);
  }

  getSelectedStateOption(state: StateDto) {
    if (this.addressState.value === state.name && !this.gotDefaultState) {
      this.gotDefaultState = true;
      this.fillCities();
    }
    return this.addressState.value === state.name;
  }

  getSelectedFederationAbbreviationOption(federationAbbreviation: string) {
    if (this.federationAbbreviation.value === federationAbbreviation && !this.gotDefaultFederationAbbreviation) {
      this.gotDefaultFederationAbbreviation = true;
      this.associationInputDto.federationAbbreviation = federationAbbreviation;
    }
    return this.federationAbbreviation.value === federationAbbreviation;
  }

  cancel(): void{
    this.router.navigate(['/federacoes']);
  }

  getSelectedCityOption(city: CityDto) {
    return this.addressCity.value === city.name;
  }
}

