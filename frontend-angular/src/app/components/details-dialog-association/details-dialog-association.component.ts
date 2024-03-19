import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-details-dialog-association',
  templateUrl: './details-dialog-association.component.html',
  styleUrls: ['./details-dialog-association.component.css']
})
export class DetailsDialogAssociationComponent {
  data: any;

  constructor(
    public activeModal: NgbActiveModal
  ) {}

  closeDialog() {
    this.activeModal.dismiss();
  }
}
