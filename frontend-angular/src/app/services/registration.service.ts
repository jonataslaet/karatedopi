import { Injectable } from '@angular/core';
import { RegistrationForm } from '../common/registration-form';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private requestService: RequestService) { }

  createRegistration(payload: RegistrationForm){
    return this.requestService.request('POST', '/registration');
  }

  deleteRegistrationByUserId(id: number) {
    const endpoint = `/registration/${id}`;
    return this.requestService.request('DELETE', endpoint);
  }
}