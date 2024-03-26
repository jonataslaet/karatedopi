import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserEvaluation } from '../common/user.evaluation';
import { UsersReadResponse } from '../common/users.read.response';
import { RequestService } from './request.service';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private requestService: RequestService) { }

    getUserListPaginate(theContent: string, theStatus: string, thePage: number, thePageSize: number,
        theField: string, theDirection: string): Observable<UsersReadResponse> {
        if (theContent != null) {
            const endpoint = `/users?search=${theContent}&status=${theStatus}&page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
            return this.requestService.request('GET', endpoint);
        }
        const endpoint = `/users?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
        return this.requestService.request('GET', endpoint);
    }

    getUserEvaluation(id: number, userEvalution: UserEvaluation) {
        const endpoint = `/users/${id}/evaluations`;
        return this.requestService.request('PUT', endpoint, userEvalution);
    }

}