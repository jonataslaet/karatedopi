import { RegistratrionFormOutputDto } from './registratrion.form.output.dto';
export interface RegistrationFormsResponse {
    content: RegistratrionFormOutputDto[];
    totalElements: number;
    totalPages: number;
    pageable: {
        pageSize: number;
        pageNumber: number;
    };
}
