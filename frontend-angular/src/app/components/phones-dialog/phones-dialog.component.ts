import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-phones-dialog',
  templateUrl: './phones-dialog.component.html',
  styleUrls: ['./phones-dialog.component.css']
})
export class PhonesDialogComponent {
  data: any;

  constructor(
    public activeModal: NgbActiveModal
  ) {}

  closeDialog() {
    this.activeModal.dismiss();
  }
}
