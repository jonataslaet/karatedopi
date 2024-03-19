import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
  selector: 'app-tournament-create',
  templateUrl: './tournament-create.component.html',
  styleUrls: ['./tournament-create.component.css']
})
export class TournamentCreateComponent implements OnInit {
  tournamentFormGroup: FormGroup;

  hide = true;
  states: StateDto[] = [];
  addressCities: CityDto[] = [];

  statuses = Object.keys(TournamentStatusEnum) as TournamentStatusEnum[];

  tournamentForm: TournamentItem = {
    id: null,
    name: '',
    status: null,
    address: null,
    eventDateTime: null,
    participants: []
  };

  constructor(
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private tournamentService: TournamentService,
    private router: Router, private cityService: CityService,
    private stateService: StateService) {}

  ngOnInit(): void {
    this.tournamentFormGroup = this.formBuilder.group({
      championship: this.formBuilder.group({
        name: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
        status: new FormControl(TournamentStatusEnum.OPENED.name, [Validators.required]),
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

    this.stateService.getStates()
    .subscribe({
      next: (response: StateDto[]) => {
        this.states = response
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
  
  createTournament() {
    this.fillTournamentForm();
    this.tournamentService.createTournament(this.tournamentForm).subscribe({
      next: () => {
        this.toastrService.success('Cadastro efetuado com sucesso.', 'Sucesso');
        this.router.navigate(['/torneios']);
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  fillTournamentForm(): void {
    this.tournamentForm.name = this.tournamentName.value;
    this.tournamentForm.status = this.getStatusByName(this.tournamentStatus.value);
    this.tournamentForm.eventDateTime = this.getFormattedDate(this.tournamentEventDate.value, this.tournamentEventTime.value);
    this.tournamentForm.address = this.tournamentFormGroup.get('address').value;
    this.tournamentForm.address.zipCode = this.addressZipCode.value;
    this.tournamentForm.address.street = this.addressStreet.value;
    this.tournamentForm.address.number = this.addressNumber.value;
    this.tournamentForm.address.neighbourhood = this.addressNeighbourhood.value;
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

  selectCityAndState(formGroupName: string) {
    const formGroup = this.tournamentFormGroup.get(formGroupName);
    this.tournamentForm.address = formGroup?.value;
    this.tournamentForm.address.city.name = formGroup?.value.city.name as string;
    this.tournamentForm.address.city.state.name = formGroup?.value.state.name as string;
  }

  fillCities(formGroupName: string) {
    const formGroup = this.tournamentFormGroup.get(formGroupName);
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
    this.router.navigate(['/']);
  }

  getStatusByName(statusName: string): TournamentStatusEnum | undefined {
    if (statusName !== null && statusName !== undefined) {
      const normalizedStatusName = statusName.toLowerCase();
      for (const key of this.statuses) {
        if (TournamentStatusEnum[key].name.toLowerCase() === normalizedStatusName) {
            return key;
        }
      }
      return undefined;
    }
    return TournamentStatusEnum[TournamentStatusEnum.OPENED.id];
  }

}
