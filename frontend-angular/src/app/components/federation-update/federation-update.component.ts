import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CityDto } from 'src/app/common/city.dto';
import { FederationOutputDto } from 'src/app/common/federation.output.dto';
import { OrganizationStatusEnum } from 'src/app/common/organization.status.enum';
import { StateDto } from 'src/app/common/state.dto';
import { CityService } from 'src/app/services/city.service';
import { FederationService } from 'src/app/services/federation.service';
import { StateService } from 'src/app/services/state.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';
import { FederationInputDto } from './../../common/federation.input.dto';

@Component({
  selector: 'app-federation-update',
  templateUrl: './federation-update.component.html',
  styleUrls: ['./federation-update.component.css']
})
export class FederationUpdateComponent implements OnInit {
  gotDefaultState: boolean = false;
  federationInputGroup: FormGroup;
  federationId: number;
  hide = true;
  addressStates: StateDto[] = [];
  addressCities: CityDto[] = [];

  statuses = Object.keys(OrganizationStatusEnum) as OrganizationStatusEnum[];

  federationInputDto: FederationInputDto = {
    businessName: '',
    tradeName: '',
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
    private federationService: FederationService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cityService: CityService,
    private stateService: StateService) {}

  ngOnInit(): void {
    this.federationInputGroup = this.formBuilder.group({
      information: this.formBuilder.group({
        businessName: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        tradeName: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
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
      this.federationId = Number(param.get('id'));
      this.getFederationById(this.federationId);
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

  get federationBusinessName() { return this.federationInputGroup.get('information.businessName');}
  get federationTradeName() { return this.federationInputGroup.get('information.tradeName'); }
  get federationAbbreviation() { return this.federationInputGroup.get('information.federationAbbreviation'); }
  get federationFoundationDate() { return this.federationInputGroup.get('information.foundationDate'); }
  get federationEin() { return this.federationInputGroup.get('information.ein'); }
  get federationEmail() { return this.federationInputGroup.get('information.email'); }
  get federationFirstPhone() { return this.federationInputGroup.get('information.firstPhone');}
  get federationSecondPhone() { return this.federationInputGroup.get('information.secondPhone');}
  get federationThirdPhone() { return this.federationInputGroup.get('information.thirdPhone');}

  get addressZipCode() { return this.federationInputGroup.get('address.zipCode');}
  get addressStreet() { return this.federationInputGroup.get('address.street');}
  get addressNumber() { return this.federationInputGroup.get('address.number');}
  get addressNeighbourhood() { return this.federationInputGroup.get('address.neighbourhood');}
  get addressCity() { return this.federationInputGroup.get('address.city');}
  get addressState() { return this.federationInputGroup.get('address.state');}
  
  updateFederation() {
    this.fillFederationInput();
    this.federationService.updateFederation(this.federationId, this.federationInputDto).subscribe({
      next: () => {
        this.toastrService.success('Cadastro efetuado com sucesso.', 'Sucesso');
        this.router.navigate(['/federacoes']);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  getFederationById(id: number) {
    this.federationService.getFederationById(id).subscribe({
      next: (data: FederationOutputDto) => {
        this.federationInputDto = data;

        this.federationBusinessName.setValue(data.businessName);
        this.federationTradeName.setValue(data.tradeName);
        this.federationAbbreviation.setValue(data.federationAbbreviation);
        this.federationFoundationDate.setValue(this.formatDateToYYYYMMDD(new Date(data.foundationDate)));
        this.federationEin.setValue(data.ein);
        this.federationEmail.setValue(data.email);
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

  fillFederationInput(): void {
    this.federationInputDto.businessName = this.federationBusinessName.value;
    this.federationInputDto.foundationDate = this.getFormattedDate(this.federationFoundationDate.value);
    this.federationInputDto.tradeName = this.federationTradeName.value;
    this.federationInputDto.federationAbbreviation = this.federationAbbreviation.value;
    this.federationInputDto.ein = this.federationEin.value;
    this.federationInputDto.email = this.federationEmail.value;
    this.addValidPhone(this.federationFirstPhone.value, this.federationSecondPhone.value, this.federationThirdPhone.value);

    this.federationInputDto.address.zipCode = this.addressZipCode.value;
    this.federationInputDto.address.street = this.addressStreet.value;
    this.federationInputDto.address.number = this.addressNumber.value;
    this.federationInputDto.address.neighbourhood = this.addressNeighbourhood.value;
    this.federationInputDto.address.city.name = this.addressCity.value;
    this.federationInputDto.address.city.state.name = this.addressState.value;
  }

  addValidPhone(phone1: string, phone2: string, phone3: string): void {
    if (this.isValidPhone(phone1)) {
      this.federationInputDto.phoneNumbers.push(phone1);
    }
    if (this.isValidPhone(phone2)) {
      this.federationInputDto.phoneNumbers.push(phone2);
    }
    if (this.isValidPhone(phone3)) {
      this.federationInputDto.phoneNumbers.push(phone3);
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
    this.federationInputDto.address.city.state = this.getStateByName(this.addressState.value);
    const stateAbbreviation = this.federationInputDto.address.city.state.stateAbbreviation;
    this.updateAddressFederationGroup();
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
    this.federationInputDto.phoneNumbers[numberId] = editPhone.value;
    this.setPhone(numberId, editPhone.value);
  }

  updateAddressFederationGroup() {
    this.federationInputGroup.patchValue({
      address: {
        street: this.federationInputDto.address.street,
        number: this.federationInputDto.address.number,
        zipCode: this.federationInputDto.address.zipCode,
        neighbourhood: this.federationInputDto.address.neighbourhood,
        state: this.federationInputDto.address.city.state.name,
        city: this.federationInputDto.address.city.name
      }
    });
  }

  private setPhone(numberId: number, editPhone: string) {
    if (numberId == 0) {
      this.federationFirstPhone.setValue(editPhone);
    } else if (numberId == 1) {
      this.federationSecondPhone.setValue(editPhone);
    } else if (numberId == 2) {
      this.federationThirdPhone.setValue(editPhone);
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

  cancel(): void{
    this.router.navigate(['/federacoes']);
  }

  getSelectedCityOption(city: CityDto) {
    return this.addressCity.value === city.name;
  }
}

