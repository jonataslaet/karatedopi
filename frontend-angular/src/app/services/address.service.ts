import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddressesReadResponse } from '../common/addresses-read-response';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private requestService: RequestService) { }

  getAddressListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<AddressesReadResponse> {
    const endpoint = `/addresses?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.requestService.request('GET', endpoint);
  }
}
