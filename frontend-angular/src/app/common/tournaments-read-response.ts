import { TournamentItem } from './tournament-item';

export interface TournamentsReadResponse {
  content: TournamentItem[],
  totalElements: number,
  totalPages: number,
  pageable: {
    pageSize: number,
    pageNumber: number
  }
}