import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TournamentsReadResponse } from '../common/tournaments-read-response';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  private baseUrl = 'http://localhost:8080/tournaments';

  constructor(private httpClient: HttpClient) { }

  getTournamentListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<TournamentsReadResponse> {
    const searchUrl = `${this.baseUrl}?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.httpClient.get<TournamentsReadResponse>(searchUrl);
  }
}