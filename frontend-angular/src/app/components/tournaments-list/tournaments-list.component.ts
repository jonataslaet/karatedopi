import { AfterViewInit, Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { jwtDecode } from 'jwt-decode';
import { ToastrService } from 'ngx-toastr';
import { TournamentItem } from 'src/app/common/tournament.item';
import { TournamentParticipant } from 'src/app/common/tournament.participant';
import { TournamentsReadResponse } from 'src/app/common/tournaments.read.response';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { TournamentService } from 'src/app/services/tournament.service';
import { ParticipateDialogTournamentComponent } from '../participate-dialog-tournament/participate-dialog-tournament.component';

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

  constructor(
    private tournamentService: TournamentService,
    private modalService: NgbModal,
    private authenticationService: AuthenticationService,
    private toastrService: ToastrService
  ) {}

  ngAfterViewInit() {
    this.listTournaments(null);
  }

  listTournaments(event?: any) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }
    this.getTournamentListPaginate(null, this.pageIndex, this.pageSize, this.sortField, this.sortDirection);
  }

  search(event: HTMLInputElement) {
    const searchedContent = event.value;
    this.getTournamentListPaginate(searchedContent, this.pageIndex, this.pageSize, this.sortField, this.sortDirection);
  }

  private getTournamentListPaginate(searchedContent: string, pageIndex: number, pageSize: number,
    sortField: string, sortDirection: string) {
    this.tournamentService
      .getTournamentListPaginate(searchedContent, pageIndex, pageSize, sortField, sortDirection)
      .subscribe({
        next: (response: TournamentsReadResponse) => {
          this.tournaments = response.content;
          this.dataLength = response.totalElements;
          this.pageIndex = response.pageable.pageNumber;
          this.pageSize = response.pageable.pageSize;
        },
        error: err => {
          this.toastrService.error(err.error.message, err.error.error);
        }
        
      });
  }

  onSortChange(sortField: string) {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.sortField = sortField;
    this.listTournaments(null);
  }

  openParticipationInTournament(id: number) {
    const modalRef = this.modalService.open(ParticipateDialogTournamentComponent, {
      size: 'sm',
      centered: true,
      backdrop: 'static',
    });

    modalRef.componentInstance.id = id;
    modalRef.componentInstance.data = { id };

    modalRef.result.then((result) => {
      if (result) {
        this.tournaments = this.tournaments.filter((tournament) => tournament.id !== id);
        this.listTournaments(null);
      }
    });
  }

  isLoggedUserParticipating(participants: TournamentParticipant[]): boolean {
    const token = localStorage.getItem('auth_token') ?? '';
    if (token && token !== null && token.length > 7) {
        const userId = jwtDecode(token)['id'];
        return participants.some((participant) => participant.id === userId);
    }
    return false;
  }

  hasPrivileges(uri: string): boolean {
    return this.authenticationService.hasPrivileges(uri);
  }

  getPages(): number[] {
    const totalPages = Math.ceil(this.dataLength / this.pageSize);
    return Array.from({ length: totalPages }, (_, i) => i);
  }
}
