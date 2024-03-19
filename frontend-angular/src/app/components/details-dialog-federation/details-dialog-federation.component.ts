import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-details-dialog-federation',
  templateUrl: './details-dialog-federation.component.html',
  styleUrls: ['./details-dialog-federation.component.css']
})
export class DetailsDialogFederationComponent {
  data: any;

  constructor(
    public activeModal: NgbActiveModal
  ) {}

  closeDialog() {
    this.activeModal.dismiss();
  }
}
