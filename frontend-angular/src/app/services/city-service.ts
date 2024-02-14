import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { City } from "../common/city";

@Injectable({
  providedIn: 'root'
})
export class CityService {
    
    private statesUrl = `${environment.apiUrl}/cities/all`;
  
    constructor(private httpClient: HttpClient) { }

    getCities(theStateCode: string): Observable<City[]> {
      const searchCitiesUrl = `${this.statesUrl}?stateAbbreviation=${theStateCode}`
      return this.httpClient.get<City[]>(searchCitiesUrl).pipe(
        map(response => response)
      );
    }
}