import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistrationFormInputDto } from '../common/registration.form.input.dto';
import { RegistrationFormsResponse } from '../common/registration.forms.response';
import { RegistratrionFormOutputDto } from '../common/registratrion.form.output.dto';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  constructor(private requestService: RequestService) {}

  createRegistration(payload: RegistrationFormInputDto) {
    return this.requestService.request('POST', '/registrationforms', payload);
  }

  uploadImage(file: File): Observable<{ headers: HttpHeaders }>  {
    const formData: FormData = new FormData();
    formData.append('file', file);
    let options = {
      headers: new HttpHeaders({
      })
    };
    options.headers.append('Content-Type', 'multipart/form-data');
    return this.requestService.request('POST', '/registrationforms/picture', formData, options);
  }

  deleteRegistrationByUserId(id: number) {
    const endpoint = `/registrationforms/${id}`;
    return this.requestService.request('DELETE', endpoint);
  }
  
  readRegistrationFormByUserId(id: number): Observable<RegistratrionFormOutputDto> {
    const endpoint = `/registrationforms/${id}`;
    return this.requestService.request('GET', endpoint);
  }

  readRegistrationForm(): Observable<RegistratrionFormOutputDto> {
    const endpoint = `/registrationforms`;
    return this.requestService.request('GET', endpoint);
  }

  updateRegistrationFormByUserId(userId: number, registrationFormDto: RegistrationFormInputDto) {
    const endpoint = `/registrationforms/${userId}`;
    return this.requestService.request('PUT', endpoint, registrationFormDto);
  }

  updateRegistrationForm(registrationFormDto: RegistrationFormInputDto) {
    const endpoint = `/registrationforms`;
    return this.requestService.request('PUT', endpoint, registrationFormDto);
  }

  getPagedRegistrationFormsDtos(
    theContent: string,
    theStatus: string,
    thePage: number,
    thePageSize: number,
    theField: string,
    theDirection: string
): Observable<RegistrationFormsResponse> {
    if (theContent != null) {
        const endpoint = `/registrationforms?search=${theContent}&status=${theStatus}&page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
        return this.requestService.request('GET', endpoint);
    }
    const endpoint = `/registrationforms?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.requestService.request('GET', endpoint);
}
}
