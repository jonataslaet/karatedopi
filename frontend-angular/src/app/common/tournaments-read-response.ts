import { TournamentReadResponse } from './tournament-read-response';

export interface TournamentsReadResponse {
  content: TournamentReadResponse[],
  totalElements: number,
  totalPages: number,
  pageable: {
    pageSize: number,
    pageNumber: number
  }
}