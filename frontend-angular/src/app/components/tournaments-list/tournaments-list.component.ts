import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { jwtDecode } from 'jwt-decode';
import { endpoints } from 'src/app/common/app.endpoints';
import { RouteItem } from 'src/app/common/route-item';
import { TournamentItem } from 'src/app/common/tournament-item';
import { TournamentParticipant } from 'src/app/common/tournament-participant';
import { TournamentsReadResponse } from 'src/app/common/tournaments-read-response';
import { ParticipateDialogTournamentComponent } from '../participate-dialog-tournament/participate-dialog-tournament.component';
import { TournamentService } from './../../services/tournament.service';

@Component({
  selector: 'app-tournaments-list',
  templateUrl: './tournaments-list.component.html',
  styleUrls: ['./tournaments-list.component.css'],
})
export class TournamentsListComponent implements AfterViewInit {
  tournaments: TournamentItem[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';

  displayedColumns: string[] = [
    'name',
    'location',
    'status',
    'numberOfParticipants',
    'eventDate',
    'eventTime',
    'actions',
  ];
  dataSource: MatTableDataSource<TournamentItem>;

  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private tournamentService: TournamentService,
    public dialog: MatDialog
  ) {
    this.dataSource = new MatTableDataSource(this.tournaments);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.listTournaments(null);
  }

  listTournaments(event?: PageEvent) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }

    this.tournamentService
      .getTournamentListPaginate(
        this.pageIndex,
        this.pageSize,
        this.sortField,
        this.sortDirection
      )
      .subscribe((response: TournamentsReadResponse) => {
        this.dataSource = new MatTableDataSource(response.content);
        this.tournaments = response.content;
        this.dataLength = response.totalElements;
        this.pageIndex = response.pageable.pageNumber;
        this.pageSize = response.pageable.pageSize;
      });
  }

  search(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onSortChange(sortState: Sort) {
    this.sortDirection = sortState.direction;
    this.sortField = sortState.active;
    this.listTournaments(null);
  }

  openParticipationInTournament(id: number) {
    const dialogRef = this.dialog.open(ParticipateDialogTournamentComponent, {
      width: '250px',
      data: { id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.tournaments = this.tournaments.filter((_) => _.id !== id);
        this.listTournaments(null);
      }
    });
  }

  isLoggedUserParticipating(participants: TournamentParticipant[]): boolean {
    const token = localStorage.getItem('auth_token') ?? '';
    if (token && token !== null && token.length > 7) {
      const isParticipant = participants.some(
        (participant) => participant.id === jwtDecode(token)['id']);
      return isParticipant;
    }
    return false;
  }

  hasPrivileges(): boolean {
    const token = localStorage.getItem('auth_token') ?? '';
    if (token && token !== null && token.length > 7) {
      let pathAuthorities: string[] = this.getAuthoritiesForPath("/tournaments/create");
      const hasPrivileges = pathAuthorities.some(
          (authority) => jwtDecode(token)['authorities'].includes(authority)
        );
        return hasPrivileges;
    }
    return false;
  }

  getAuthoritiesForPath(path: string): string[] {
    const matchingMenu = endpoints.routes.find((menu: RouteItem) => menu.path === path);

    if (matchingMenu) {
      return matchingMenu.authorities || [];
    }

    return [];
  }
}
