import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GetResponseProfiles } from '../common/get-response-profiles';
import { Profile } from '../common/profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private baseUrl = 'http://localhost:8080/profiles';

  constructor(private httpClient: HttpClient) { }

  getProfileListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<GetResponseProfiles> {
    const searchUrl = `${this.baseUrl}?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.httpClient.get<GetResponseProfiles>(searchUrl);
  }

  getProfileById(id: number): Observable<Profile> {
    return this.httpClient.get<Profile>(`${this.baseUrl}/${id}`);
  }
   
  updateProfile(payload: Profile): Observable<Profile> {
    console.log('d = ' + payload.birthday);
    const updateUrl = `${this.baseUrl}/${payload.id}`;
    console.log(payload.id);
    return this.httpClient.put<Profile>(updateUrl, payload);
  }
  
}