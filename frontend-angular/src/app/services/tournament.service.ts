import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TournamentItem } from '../common/tournament.item';
import { TournamentsReadResponse } from '../common/tournaments.read.response';
import { RequestService } from './request.service';

@Injectable({
    providedIn: 'root'
})
export class TournamentService {

    constructor(private requestService: RequestService) { }

    createTournament(tournamentForm: TournamentItem) {
        return this.requestService.request('POST', '/tournaments', tournamentForm);
    }

    getTournamentListPaginate(theContent: string, theStatus: string, thePage: number, thePageSize: number,
        theField: string, theDirection: string): Observable<TournamentsReadResponse> {
        if (theContent != null) {
            const endpoint = `/tournaments?search=${theContent}&status=${theStatus}&page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
            return this.requestService.request('GET', endpoint);
        }
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

    getTournamentById(id: number): Observable<TournamentItem> {
        const endpoint = `/tournaments/${id}`;
        return this.requestService.request('GET', endpoint, id);
    }

    updateTournament(payload: TournamentItem): Observable<TournamentItem> {
        const endpoint = `/tournaments/${payload.id}`;
        return this.requestService.request('PUT', endpoint, payload);
    }
}