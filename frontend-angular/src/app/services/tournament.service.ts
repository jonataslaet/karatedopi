import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TournamentsReadResponse } from '../common/tournaments-read-response';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  constructor(private requestService: RequestService) { }

  getTournamentListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<TournamentsReadResponse> {
    const endpoint = `/tournaments?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.requestService.request('GET', endpoint);
  }

  patchTournamentListPaginate(thePage: number, thePageSize: number, theField: string, theDirection: string): Observable<TournamentsReadResponse> {
    const endpoint = `/tournaments?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
    return this.requestService.request('GET', endpoint);
  }

  toggleParticipantInTournament(id: number) {
    const endpoint = `/tournaments/${id}/participations`;
    return this.requestService.request('PATCH', endpoint);
  }

  findAllParticipantsInTournament(id: number) {
    const endpoint = `/tournaments/${id}/participations`;
    return this.requestService.request('GET', endpoint);
  }
}