import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationResponse } from 'src/app/common/authentication.response';
import { BeltEnum } from 'src/app/common/belt.enum';
import { GraduationDTO } from 'src/app/common/graduation.dto';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-graduate-dialog',
  templateUrl: './graduate-dialog.component.html',
  styleUrls: ['./graduate-dialog.component.css']
})
export class GraduateDialogComponent implements OnInit {
  belts: { name: string, value: string }[] = Object.entries(BeltEnum).map(([value, name]) => ({ name, value }));
  data: any;

  graduationDto: GraduationDTO = {
      belt: null
  }

  constructor(
    private toastrService: ToastrService,
    public activeModal: NgbActiveModal,
    private profileService: ProfileService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.authenticationService.getAuthenticatedUser()
        .subscribe({
            next: (response: AuthenticationResponse) => {
                this.authenticationService.currentUserSignal.set(response);
            },
            error: err => {
              this.toastrService.error(err.error.message, err.error.error);
            }
        });
  }

  confirmEvaluation() {
    this.profileService.changeGraduation(this.data.id, this.graduationDto)
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
