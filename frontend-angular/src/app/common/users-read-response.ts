import { UserReadResponse } from './user-read-response';
export interface UsersReadResponse {
    content: UserReadResponse[];
    totalElements: number;
    totalPages: number;
    pageable: {
        pageSize: number;
        pageNumber: number;
    };
}
