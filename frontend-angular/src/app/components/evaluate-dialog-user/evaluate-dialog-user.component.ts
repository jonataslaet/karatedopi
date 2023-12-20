import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { UserEvaluation } from 'src/app/common/user-evaluation';
import { UserRoleEnum } from 'src/app/common/user-role-enum';
import { UserStatusEnum } from 'src/app/common/user-status-enum';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-evaluate-dialog-user',
  templateUrl: './evaluate-dialog-user.component.html',
  styleUrls: ['./evaluate-dialog-user.component.css']
})
export class EvaluateDialogUserComponent implements OnInit {

  statuses: { name: string, value: string }[] = Object.entries(UserStatusEnum).map(([value, name]) => ({ name, value }));
  roles: { name: string, value: string }[] = Object.entries(UserRoleEnum).map(([value, name]) => ({ name, value }));


  userEvalution: UserEvaluation = {
    status: null,
    authority: null
  }

  constructor(
    public dialogRef: MatDialogRef<EvaluateDialogUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private authenticationService: AuthenticationService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser()
      .subscribe({
        next: (response: AuthenticationResponse) => {
          this.authenticationService.currentUserSignal.set(response);
        }
      });
  }
 
  confirmEvaluation() {
    this.userService.getUserEvaluation(this.data.id, this.userEvalution)
    .subscribe({
      next: () => {
        this.dialogRef.close(this.data.id);
      }
    });
  }
  

}
