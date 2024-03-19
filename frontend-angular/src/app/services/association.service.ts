import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AssociationInputDto } from '../common/association.input.dto';
import { AssociationOutputDto } from '../common/association.output.dto';
import { AssociationsResponse } from '../common/associations.response';
import { RequestService } from './request.service';

@Injectable({
    providedIn: 'root',
})
export class AssociationService {
        
    constructor(private requestService: RequestService) {}

    getAllAssociationAbbreviations() {
        return this.requestService.request('GET', '/associations/abbreviations/all');
    }

    getPagedAssociationsDtos(
    federationId: number,
    theContent: string,
    thePage: number,
    thePageSize: number,
    theField: string,
    theDirection: string
    ): Observable<AssociationsResponse> {
        if (theContent != null) {
            const endpoint = `/federations/${federationId}/associations?search=${theContent}&page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
            return this.requestService.request('GET', endpoint);
        }
        const endpoint = `/federations/${federationId}/associations?page=${thePage}&size=${thePageSize}&sort=${theField},${theDirection}`;
        return this.requestService.request('GET', endpoint);
    }

    updateAssociation(associationId: number, payload: AssociationInputDto): Observable<AssociationOutputDto> {
        const endpoint = `/associations/${associationId}`;
        return this.requestService.request('PUT', endpoint, payload);
    }

    getAssociationById(associationId: number): Observable<AssociationOutputDto> {
        const endpoint = `/associations/${associationId}`;
        return this.requestService.request('GET', endpoint, associationId);
    }

    createAssociation(associationInputDto: AssociationInputDto): Observable<AssociationOutputDto> {
        return this.requestService.request('POST', '/associations', associationInputDto);
    }
}
