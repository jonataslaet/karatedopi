import { AddressReadResponse } from './address-read-response';

export interface AddressesReadResponse {
  content: AddressReadResponse[],
  totalElements: number,
  totalPages: number,
  pageable: {
    pageSize: number,
    pageNumber: number
  }
}