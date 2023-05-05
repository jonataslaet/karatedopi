import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegistrationForm } from '../common/registration-form';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private baseUrl = 'http://localhost:8080/registration';

  constructor(private httpClient: HttpClient) { }

  createRegistration(payload: RegistrationForm){
    const creationUrl = `${this.baseUrl}`;
    return this.httpClient.post<RegistrationForm>(creationUrl, payload);
  }

  deleteRegistrationByUserId(id: number) {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}