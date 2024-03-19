import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CityDto } from 'src/app/common/city.dto';
import { StateDto } from 'src/app/common/state.dto';
import { TournamentItem } from 'src/app/common/tournament.item';
import { TournamentStatusEnum } from 'src/app/common/tournament.status.enum';
import { CityService } from 'src/app/services/city.service';
import { StateService } from 'src/app/services/state.service';
import { TournamentService } from 'src/app/services/tournament.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-tournament-update',
  templateUrl: './tournament-update.component.html',
  styleUrls: ['./tournament-update.component.css']
})
export class TournamentUpdateComponent implements OnInit, AfterViewInit {
  tournamentFormGroup: FormGroup;
  gotDefaultCity: boolean = false;
  gotDefaultState: boolean = false;
  gotDefaultStatus: boolean = false;
  tournamentStatusEnum = TournamentStatusEnum;
  statuses = Object.keys(TournamentStatusEnum) as TournamentStatusEnum[];

  addressStates: StateDto[] = [];
  addressCities: CityDto[] = [];

  tournamentItem: TournamentItem = {
    id: null,
    name: '',
    status: null,
    eventDateTime: null,
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
    participants: null
  };

  constructor(
    private formBuilder: FormBuilder,
    private tournamentService: TournamentService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cityService: CityService,
    private stateService: StateService,
    private toastrService: ToastrService
    ) {}

  ngAfterViewInit(): void {
    this.gotDefaultCity = false;
    this.gotDefaultState = false;
    this.gotDefaultStatus = false;
  }

  ngOnInit(): void {

    this.tournamentFormGroup = this.formBuilder.group({
      championship: this.formBuilder.group({
        name: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        status: new FormControl('', [Validators.required]),
        eventDate: new FormControl('', [Validators.required]),
        eventTime: new FormControl('', [Validators.required]),
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
      var id = Number(param.get('id'));
      this.getTournamentById(id);
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

  get tournamentName() { return this.tournamentFormGroup.get('championship.name');}
  get tournamentStatus() { return this.tournamentFormGroup.get('championship.status');}
  get tournamentEventDate() { return this.tournamentFormGroup.get('championship.eventDate');}
  get tournamentEventTime() { return this.tournamentFormGroup.get('championship.eventTime');}

  get addressZipCode() { return this.tournamentFormGroup.get('address.zipCode');}
  get addressStreet() { return this.tournamentFormGroup.get('address.street');}
  get addressNumber() { return this.tournamentFormGroup.get('address.number');}
  get addressNeighbourhood() { return this.tournamentFormGroup.get('address.neighbourhood');}
  get addressCity() { return this.tournamentFormGroup.get('address.city');}
  get addressState() { return this.tournamentFormGroup.get('address.state');}

  getTournamentById(id: number) {
    this.tournamentService.getTournamentById(id).subscribe({
      next: (data: TournamentItem) => {
        this.tournamentItem = data;
        this.tournamentName.setValue(data.name);
        this.tournamentStatus.setValue(this.getStatusByName(this.tournamentStatus.value));
        this.tournamentEventDate.setValue(this.formatDateToYYYYMMDD(new Date(data.eventDateTime)));
        this.tournamentEventTime.setValue(this.formatDateToHHMM(new Date(data.eventDateTime)));
        this.addressCity.setValue(data.address.city);
        this.addressZipCode.setValue(data.address.zipCode);
        this.addressStreet.setValue(data.address.street);
        this.addressNumber.setValue(data.address.number);
        this.addressNeighbourhood.setValue(data.address.neighbourhood);
        this.addressState.setValue(data.address.city.state);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  updateTournament() {
    this.updateTournamentItem();

    this.tournamentService.updateTournament(this.tournamentItem)
    .subscribe({
      next: (response: TournamentItem) => {
        this.tournamentItem = response,
        this.router.navigate(['/torneios']);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  updateTournamentItem() {
    this.tournamentItem.name = this.tournamentName.value;
    this.tournamentItem.status = this.getStatusByName(this.tournamentStatus.value);
    this.tournamentItem.eventDateTime = this.getFormattedDate(this.tournamentEventDate.value, this.tournamentEventTime.value);
    this.tournamentItem.address.zipCode = this.addressZipCode.value;
    this.tournamentItem.address.street = this.addressStreet.value;
    this.tournamentItem.address.number = this.addressNumber.value;
    this.tournamentItem.address.neighbourhood = this.addressNeighbourhood.value;
    this.tournamentItem.address.city.name = this.addressCity.value;
    this.tournamentItem.address.city.state.name = this.addressState.value;
  }
  
  getFormattedDate(dateString: string, timeString: string): Date | null {
    const day = parseInt(dateString.substring(8, 10), 10);
    const month = parseInt(dateString.substring(5, 7), 10) - 1;
    const year = parseInt(dateString.substring(0, 4), 10);
    const [hour, minute] = timeString.split(':');
    const date = new Date();
    date.setUTCFullYear(year);
    date.setUTCMonth(month);
    date.setUTCDate(day);
    date.setUTCHours(parseInt(hour, 10));
    date.setUTCMinutes(parseInt(minute, 10));
  
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

  formatDateToHHMM(date: Date): string {
    const hours = date.getUTCHours().toString().padStart(2, '0');
    const minutes = date.getUTCMinutes().toString().padStart(2, '0');
    const formattedTime = `${hours}:${minutes}`;
    return formattedTime;
  }

  cancel(): void{
    this.router.navigate(['/torneios']);
  }

  fillCities(event: Event) {
    this.tournamentItem.address.city.state = this.getStateByName(this.addressState.value);
    const stateAbbreviation = this.tournamentItem.address.city.state.stateAbbreviation;
    this.updateAddressTournamentFormGroup();
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

  selectCityAndState() {
    const formGroup = this.tournamentFormGroup.get('address');
    this.tournamentItem.address.city = formGroup?.value.city;
    this.tournamentItem.address.city.state = formGroup?.value.state;
  }

  updateChampionshipTournamentFormGroup() {
    this.tournamentFormGroup.patchValue({
      championship: {
        id: this.tournamentItem.id,
        name: this.tournamentItem.name,
        status: this.tournamentItem.status,
        participants: this.tournamentItem.participants,
        eventDateTime: this.tournamentItem.eventDateTime
      }});
  }

  updateAddressTournamentFormGroup() {
    this.tournamentFormGroup.patchValue({
      address: {
        street: this.tournamentItem.address.street,
        number: this.tournamentItem.address.number,
        zipCode: this.tournamentItem.address.zipCode,
        neighbourhood: this.tournamentItem.address.neighbourhood,
        state: this.tournamentItem.address.city.state.name,
        city: this.tournamentItem.address.city.name
      }
    });
  }

  getSelectedStateOption(state: StateDto) {
    if (state.id === this.tournamentItem.address.city.state.id && !this.gotDefaultState) {
      this.gotDefaultState = true;
      this.updateAddressTournamentFormGroup();
      this.cityService.getCities(state.stateAbbreviation as string)
        .subscribe({
          next: (response: CityDto[]) => {
            this.addressCities = response
          },
          error: err => {
            this.toastrService.error(err.error.message, err.error.error);
          }
        });
    }
    return state.id === this.tournamentItem.address.city.state.id;
  }

  getStateByName(stateName: string): StateDto | undefined {
    return this.addressStates.find((state) => state.name === stateName);
  }

  getStatusByName(statusName: string): TournamentStatusEnum | undefined {
    if (this.tournamentStatus.value !== null && this.tournamentStatus.value !== undefined) {
      const normalizedStatusName = statusName.toLowerCase();
      for (const key of this.statuses) {
        if (TournamentStatusEnum[key].name.toLowerCase() === normalizedStatusName) {
            return key;
        }
      }
      return undefined;
    }
    return this.tournamentItem.status;
  }
}
