import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProfileInput } from '../common/profile-input';
import { ProfileReadResponse } from '../common/profile-read-response';
import { ProfileUpdateResponse } from '../common/profile-update-response';
import { ProfilesReadResponse } from '../common/profiles-read-response';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private baseUrl = 'http://localhost:8080/profiles';

  constructor(private httpClient: HttpClient, private requestService: RequestService) { }

  // getProfileListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<ProfilesReadResponse> {
  //   const searchUrl = `${this.baseUrl}?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
  //   return this.httpClient.get<ProfilesReadResponse>(searchUrl);
  // }

  getProfileListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<ProfilesReadResponse> {
    const endpoint = `/profiles?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.requestService.request('GET', endpoint);
  }

  getProfileById(id: number): Observable<ProfileReadResponse> {
    return this.httpClient.get<ProfileReadResponse>(`${this.baseUrl}/${id}`);
  }
   
  updateProfile(payload: ProfileInput): Observable<ProfileUpdateResponse> {
    console.log('d = ' + payload.birthday);
    const updateUrl = `${this.baseUrl}/${payload.id}`;
    console.log(payload.id);
    return this.httpClient.put<ProfileUpdateResponse>(updateUrl, payload);
  }
  
}