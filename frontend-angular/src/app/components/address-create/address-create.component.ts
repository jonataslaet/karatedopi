import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormGroupDirective } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CityDto } from 'src/app/common/city.dto';
import { StateDto } from 'src/app/common/state.dto';
import { CityService } from 'src/app/services/city.service';
import { StateService } from 'src/app/services/state.service';

@Component({
  selector: 'app-address-create',
  templateUrl: './address-create.component.html',
  styleUrls: ['./address-create.component.css']
})
export class AddressCreateComponent implements OnInit {

  @Input() formGroupName: string;

  formInputGroup: FormGroup;

  addressCities: CityDto[];
  addressStates: StateDto[];

  constructor(
    private cityService: CityService,
    private toastrService: ToastrService,
    private rootFormGroup: FormGroupDirective,
    private stateService: StateService
  ) { }

  ngOnInit(): void {
    this.formInputGroup = this.rootFormGroup.control.get(this.formGroupName) as FormGroup;
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

  fillCities() {
    const stateAbbreviation = this.formInputGroup.value.state.stateAbbreviation as string;
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

  getStateByName(stateName: string): StateDto | undefined {
    return this.addressStates.find((state) => state.name === stateName);
  }
}
