import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-delete-dialog-profile',
  templateUrl: './delete-dialog-profile.component.html',
  styleUrls: ['./delete-dialog-profile.component.css']
})
export class DeleteDialogProfileComponent {

  constructor(
    public dialogRef: MatDialogRef<DeleteDialogProfileComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {}
 
  confirmDelete() {
    this.userService.deleteUser(this.data.id).subscribe(() => {
      this.dialogRef.close(this.data.id);
    });
  }
}
