import { FederationOutputDto } from './federation.output.dto';
export interface FederationsResponse {
    content: FederationOutputDto[];
    totalElements: number;
    totalPages: number;
    pageable: {
        pageSize: number;
        pageNumber: number;
    };
}
