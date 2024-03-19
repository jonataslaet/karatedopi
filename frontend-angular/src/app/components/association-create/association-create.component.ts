import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CityDto } from 'src/app/common/city.dto';
import { OrganizationStatusEnum } from 'src/app/common/organization.status.enum';
import { StateDto } from 'src/app/common/state.dto';
import { AssociationService } from 'src/app/services/association.service';
import { CityService } from 'src/app/services/city.service';
import { FederationService } from 'src/app/services/federation.service';
import { StateService } from 'src/app/services/state.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';
import { AssociationInputDto } from './../../common/association.input.dto';

@Component({
  selector: 'app-association-create',
  templateUrl: './association-create.component.html',
  styleUrls: ['./association-create.component.css']
})
export class AssociationCreateComponent implements OnInit {
  associationInputGroup: FormGroup;
  federationAbbreviations: string[] = [];
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
    private router: Router, private cityService: CityService,
    private activatedRoute: ActivatedRoute,
    private stateService: StateService) {}

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


  get addressStreet() { return this.associationInputGroup.get('address.street');}
  get addressNumber() { return this.associationInputGroup.get('address.number');}
  get addressZipCode() { return this.associationInputGroup.get('address.zipCode');}
  get addressNeighbourhood() { return this.associationInputGroup.get('address.neighbourhood');}
  get addressCity() { return this.associationInputGroup.get('address.city');}
  get addressState() { return this.associationInputGroup.get('address.state');}
  
  createAssociation() {
    this.fillAssociationInput();
    this.associationService.createAssociation(this.associationInputDto).subscribe({
      next: () => {
        this.toastrService.success('Cadastro efetuado com sucesso.', 'Sucesso');
        this.router.navigate(['/federacoes']);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
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
    this.associationInputDto.address.city = this.addressCity.value;
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

  fillCities(formGroupName: string) {
    const formGroup = this.associationInputGroup.get(formGroupName);
    const stateCode = formGroup?.value.state.stateAbbreviation as string;
    this.cityService.getCities(stateCode)
    .subscribe({
      next: (response: CityDto[]) => {
        this.addressCities = response
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    })
    ;
  }

  cancel(): void{
    this.router.navigate(['/federacoes']);
  }

}

