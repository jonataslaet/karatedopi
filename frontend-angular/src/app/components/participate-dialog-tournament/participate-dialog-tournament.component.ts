import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { jwtDecode } from 'jwt-decode';
import { ToastrService } from 'ngx-toastr';
import { TournamentParticipant } from 'src/app/common/tournament.participant';
import { TournamentService } from 'src/app/services/tournament.service';

@Component({
  selector: 'app-participate-dialog-tournament',
  templateUrl: './participate-dialog-tournament.component.html',
  styleUrls: ['./participate-dialog-tournament.component.css'],
})
export class ParticipateDialogTournamentComponent implements OnInit {
  tournamentParticipants: TournamentParticipant[] = [];
  isParticipant: boolean = false;
  data: any;
  constructor(
    public activeModal: NgbActiveModal,
    private tournamentService: TournamentService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.tournamentService.findAllParticipantsInTournament(this.data.id).subscribe({
      next: (response: TournamentParticipant[]) => {
        this.tournamentParticipants = response;
        const token = localStorage.getItem('auth_token') ?? '';
        if (token && token !== null && token.length > 7) {
          this.isParticipant = this.tournamentParticipants.some((participant) => participant.id === jwtDecode(token)['id']);
        }
      },
      error: err => {
        this.toastrService.error(err.error.message, err.error.error);
      }
    });
  }

  toggleParticipation() {
    this.tournamentService
      .toggleParticipantInTournament(this.data.id)
      .subscribe({
        next: () => {
          this.activeModal.close(this.data.id);
        },
        error: err => {
          this.toastrService.error(err.error.message, err.error.error);
        }
      });
  }

  closeDialog() {
    this.activeModal.close(this.data.id);
  }
}
