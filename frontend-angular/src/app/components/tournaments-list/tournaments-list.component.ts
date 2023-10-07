import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { TournamentReadResponse } from 'src/app/common/tournament-read-response';
import { TournamentsReadResponse } from 'src/app/common/tournaments-read-response';
import { TournamentService } from './../../services/tournament.service';

@Component({
  selector: 'app-tournaments-list',
  templateUrl: './tournaments-list.component.html',
  styleUrls: ['./tournaments-list.component.css']
})
export class TournamentsListComponent {
  tournaments: TournamentReadResponse[] = [
    // new TournamentReadResponse(1, 'Torneio X II', 'Teresina', 'Em andamento', 3, new Date(2, 3, 4), '23:22'),
  ];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';

  displayedColumns: string[] = [
    'name', 'location', 'status', 'numberOfParticipants', 'eventDate', 'eventTime'
  ];
  dataSource: MatTableDataSource<TournamentReadResponse>;

  @ViewChild(MatSort) sort: MatSort;

  constructor(private tournamentService: TournamentService, public dialog: MatDialog, private router: Router) {
    // this.dataSource = new MatTableDataSource(this.tournaments);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.listTournaments(null);
  }

  listTournaments(event?:PageEvent) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }

    this.tournamentService.getTournamentListPaginate(this.pageIndex, this.pageSize, this.sortField, this.sortDirection)
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

  // openDeleteModal(id: number) {
  //   const dialogRef = this.dialog.open(DeleteDialogProfileComponent, {
  //     width: '250px',
  //     data: { id },
  //   });
 
  //   dialogRef.afterClosed().subscribe((result) => {
  //     if (result) {
  //       this.profiles = this.profiles.filter(
  //         (_) => _.id !== id
  //       );
  //       this.listTournaments(null);
  //     }
  //   });
  // }
}
