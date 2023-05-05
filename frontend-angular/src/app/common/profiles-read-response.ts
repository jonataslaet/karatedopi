import { ProfileReadResponse } from "./profile-read-response";
export interface ProfilesReadResponse {
  content: ProfileReadResponse[],
  totalElements: number,
  totalPages: number,
  pageable: {
    pageSize: number,
    pageNumber: number
  }
}