import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { jwtDecode } from 'jwt-decode';
import { TournamentParticipant } from 'src/app/common/tournament-participant';
import { TournamentService } from 'src/app/services/tournament.service';

@Component({
  selector: 'app-participate-dialog-tournament',
  templateUrl: './participate-dialog-tournament.component.html',
  styleUrls: ['./participate-dialog-tournament.component.css'],
})
export class ParticipateDialogTournamentComponent implements OnInit {
  tournamentParticipants: TournamentParticipant[] = [];
  isParticipant: boolean = false;
  constructor(
    public dialogRef: MatDialogRef<ParticipateDialogTournamentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private tournamentService: TournamentService
  ) {}

  ngOnInit(): void {
    this.tournamentService.findAllParticipantsInTournament(this.data.id).subscribe({
      next: (response: TournamentParticipant[]) => {
        this.tournamentParticipants = response;
        const token = localStorage.getItem('auth_token') ?? '';
        if (token && token !== null && token.length > 7) {
          this.isParticipant = this.tournamentParticipants.some((participant) => participant.id === jwtDecode(token)['id']);
        }
      }
    });
  }

  toggleParticipation() {
    this.tournamentService
      .toggleParticipantInTournament(this.data.id)
      .subscribe(() => {
        this.dialogRef.close(this.data.id);
      });
  }
}
