import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { City } from 'src/app/common/city';
import { State } from 'src/app/common/state';
import { TournamentForm } from 'src/app/common/tournament-form';
import { TournamentStatusEnum } from 'src/app/common/tournament-status-enum';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { CityService } from 'src/app/services/city-service';
import { StateService } from 'src/app/services/state-service';
import { TournamentService } from 'src/app/services/tournament.service';
import { KaratedopiValidators } from 'src/app/validators/karatedopi-validators';

@Component({
  selector: 'app-create-tournament',
  templateUrl: './create-tournament.component.html',
  styleUrls: ['./create-tournament.component.css']
})
export class CreateTournamentComponent implements OnInit {
  hide = true;
  states: State[] = [];
  addressCities: City[] = [];

  tournamentStatusEnum = TournamentStatusEnum;

  tournamentForm: TournamentForm = {
    id: null,
    name: '',
    status: null,
    address: null,
    eventDateTime: null
  };

  tournamentFormGroup = new FormGroup({
    championship: new FormGroup({
      name: new FormControl('', [Validators.required, KaratedopiValidators.notOnlyWhitespace]),
      status: new FormControl('', [Validators.required]),
      eventDateTime: new FormControl('', [Validators.required]),
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
    private snackBar: MatSnackBar, private tournamentService: TournamentService,
    private router: Router, private authenticationService: AuthenticationService,
    private cityService: CityService, private stateService: StateService) {}

  ngOnInit(): void {
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

  get tournamentEventDateTime() { return this.tournamentFormGroup.get('championship.eventDateTime');}
  
  createTournament() {
    if (this.tournamentFormGroup.invalid) {
      this.tournamentFormGroup.markAllAsTouched();
      return;
    }
    this.tournamentForm.eventDateTime = this.getDateFormat(this.tournamentEventDateTime.value);
    this.tournamentService.createTournament(this.tournamentForm).subscribe(() => {
      this.snackBar.open('Cadastro efetuado com sucesso.','✅')._dismissAfter(3000);
      this.router.navigate(['/tournaments']);
    });
  }

  selectCityAndState(formGroupName: string) {
    const formGroup = this.tournamentFormGroup.get(formGroupName);
    this.tournamentForm.address = formGroup?.value;
    this.tournamentForm.address.city = formGroup?.value.city.name as string;
    this.tournamentForm.address.state = formGroup?.value.state.name as string;
  }

  getDateFormat(eventDateTime: string): Date | null {
    if (/^\d{12}$/.test(eventDateTime)) {
      const day = parseInt(eventDateTime.substring(0, 2), 10);
      const month = parseInt(eventDateTime.substring(2, 4), 10) - 1;
      const year = parseInt(eventDateTime.substring(4, 8), 10);
      const hour = parseInt(eventDateTime.substring(8, 10), 10);
      const minute = parseInt(eventDateTime.substring(10, 12), 10);

      const date = new Date();
      date.setUTCFullYear(year);
      date.setUTCMonth(month);
      date.setUTCDate(day);
      date.setUTCHours(hour);
      date.setUTCMinutes(minute);

      if (!isNaN(date.getTime())) {
        return date;
      }
    }
    return null;
  }

  fillCities(formGroupName: string) {
    const formGroup = this.tournamentFormGroup.get(formGroupName);
    const stateCode = formGroup?.value.state.stateAbbreviation as string;
    this.cityService.getCities(stateCode)
    .subscribe({
      next: (response: City[]) => {
        this.addressCities = response
      },
      error: err => {
        alert(`There was an error: ${err.message}`);
      }
    })
    ;
  }

  cancel(): void{
    this.router.navigate(['/']);
  }

}
