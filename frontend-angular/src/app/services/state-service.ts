import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { State } from "../common/state";
import { map } from 'rxjs/operators';
import { StatesReadResponse } from '../common/states-read-response';

@Injectable({
    providedIn: 'root'
  })
export class StateService {
    private statesUrl = 'http://localhost:8080/states/all';
  
    constructor(private httpClient: HttpClient) { }

    getStates(): Observable<State[]> {
        return this.httpClient.get<State[]>(this.statesUrl).pipe(
            map(response => response)
        );
    }
}