import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TournamentsReadResponse } from '../common/tournaments-read-response';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  constructor(private requestService: RequestService, private httpClient: HttpClient) { }

  getTournamentListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<TournamentsReadResponse> {
    const endpoint = `/tournaments?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.requestService.request('GET', endpoint);
  }
}