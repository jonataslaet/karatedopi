import { Profile } from "./profile";

export interface GetResponseProfiles {
  content: Profile[],
  totalElements: number,
  totalPages: number,
  pageable: {
    pageSize: number,
    pageNumber: number
  }
}