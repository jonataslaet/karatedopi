import { AssociationOutputDto } from './association.output.dto';
export interface AssociationsResponse {
    content: AssociationOutputDto[];
    totalElements: number;
    totalPages: number;
    pageable: {
        pageSize: number;
        pageNumber: number;
    };
}
