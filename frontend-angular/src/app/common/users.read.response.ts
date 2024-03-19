import { UserOutputDto } from './user.output.dto';
export interface UsersReadResponse {
    content: UserOutputDto[];
    totalElements: number;
    totalPages: number;
    pageable: {
        pageSize: number;
        pageNumber: number;
    };
}
