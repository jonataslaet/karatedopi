import { Injectable } from '@angular/core';
import { RequestService } from './request.service';

@Injectable({
    providedIn: 'root',
})
export class StateService {
    constructor(private requestService: RequestService) {}

    getStates() {
        return this.requestService.request('GET', '/states/all');
    }
}
