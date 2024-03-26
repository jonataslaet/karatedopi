import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FederationInputDto } from '../common/federation.input.dto';
import { FederationOutputDto } from '../common/federation.output.dto';
import { FederationsResponse } from '../common/federations.response';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class FederationService {
  
  
  constructor(private requestService: RequestService) {}

  getPagedFederationsDtos(
    theContent: string,
    theStatus: string,
    thePage: number,
    thePageSize: number,
    theField: string,
    theDirection: string
  ): Observable<FederationsResponse> {
      if (theContent != null) {
          const endpoint = `/federations?search=${theContent}&status=${theStatus}&page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
          return this.requestService.request('GET', endpoint);
      }
      const endpoint = `/federations?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
      return this.requestService.request('GET', endpoint);
  }

  createFederation(federationInputDto: FederationInputDto): Observable<FederationOutputDto> {
    return this.requestService.request('POST', '/federations', federationInputDto);
  }

  getFederationById(id: number): Observable<FederationOutputDto> {
    const endpoint = `/federations/${id}`;
    return this.requestService.request('GET', endpoint, id);
  }

  updateFederation(federationId: number, payload: FederationInputDto): Observable<FederationOutputDto> {
    const endpoint = `/federations/${federationId}`;
    return this.requestService.request('PUT', endpoint, payload);
  }

  getAllFederationAbbreviations() {
    return this.requestService.request('GET', '/federations/abbreviations/all');
  }
    
}
