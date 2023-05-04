import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GetResponseProfiles } from '../common/get-response-profiles';
import { Profile } from '../common/profile';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/users';

  constructor(private httpClient: HttpClient) { }

  createProfile(payload: Profile){
    const creationUrl = `${this.baseUrl}`;
    return this.httpClient.post<Profile>(creationUrl, payload);
  }

  deleteUser(id: number) {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}