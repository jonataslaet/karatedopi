import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-delete-dialog-profile',
  templateUrl: './delete-dialog-profile.component.html',
  styleUrls: ['./delete-dialog-profile.component.css'],
})
export class DeleteDialogProfileComponent {

  data: any;

  constructor(
    private toastrService: ToastrService,
    public activeModal: NgbActiveModal,
    private registrationService: RegistrationService
  ) {}

  ngOnInit(): void {}

  confirmDelete() {
    this.registrationService
      .deleteRegistrationByUserId(this.data.id)
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
