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

  constructor(private requestService: RequestService) { }

  getProfileListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<ProfilesReadResponse> {
    const endpoint = `/profiles?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.requestService.request('GET', endpoint);
  }

  getProfileById(id: number): Observable<ProfileReadResponse> {
    const endpoint = `/profiles/${id}`;
    return this.requestService.request('GET', endpoint, id);
  }

  updateProfile(payload: ProfileInput): Observable<ProfileUpdateResponse> {
    const endpoint = `/profiles/${payload.id}`;
    return this.requestService.request('PUT', endpoint, payload);
  }
  
}