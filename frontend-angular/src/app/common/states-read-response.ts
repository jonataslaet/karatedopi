import { State } from "./state";
export interface StatesReadResponse {
  content: State[],
  totalElements: number,
  totalPages: number,
  pageable: {
    pageSize: number,
    pageNumber: number
  }
}