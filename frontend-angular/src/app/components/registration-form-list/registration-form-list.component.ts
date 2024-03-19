import { AfterViewInit, Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { RegistrationFormsResponse } from 'src/app/common/registration.forms.response';
import { RegistratrionFormOutputDto } from 'src/app/common/registratrion.form.output.dto';
import { RegistrationService } from 'src/app/services/registration.service';
import { DeleteDialogProfileComponent } from '../delete-dialog-profile/delete-dialog-profile.component';
import { GraduateDialogComponent } from '../graduate-dialog/graduate-dialog.component';
import { PhonesDialogComponent } from '../phones-dialog/phones-dialog.component';

@Component({
  selector: 'app-registration-form-list',
  templateUrl: './registration-form-list.component.html',
  styleUrls: ['./registration-form-list.component.css']
})
export class RegistrationFormListComponent implements AfterViewInit {
  registrationFormsDtos: RegistratrionFormOutputDto[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';

  constructor(
    private registrationService: RegistrationService,
    private modalService: NgbModal,
    private toastrService: ToastrService
  ) {}

  ngAfterViewInit() {
    this.listProfiles(null);
  }

  listProfiles(event?: any) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }
    this.getPagedRegistrationFormsDtos(
      null,
      this.pageIndex,
      this.pageSize,
      this.sortField,
      this.sortDirection);
  }

  search(event: HTMLInputElement) {
    const searchedContent = event.value;
    this.getPagedRegistrationFormsDtos(searchedContent,this.pageIndex,
      this.pageSize,
      this.sortField,
      this.sortDirection);
  }

  private getPagedRegistrationFormsDtos(
    searchedContent: string,
    pageIndex: number,
    pageSize: number,
    sortField: string,
    sortDirection: string
    ) {
    this.registrationService.getPagedRegistrationFormsDtos(searchedContent,
      pageIndex,
      pageSize,
      sortField,
      sortDirection)
      .subscribe({
        next: (response: RegistrationFormsResponse) => {
          this.registrationFormsDtos = response.content;
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
    this.listProfiles(null);
  }

  openDeleteModal(id: number) {
    const modalRef = this.modalService.open(DeleteDialogProfileComponent, {
        size: 'sm',
        centered: true,
        backdrop: 'static',
    });

    modalRef.componentInstance.id = id;
    modalRef.componentInstance.data = { id };

    modalRef.result.then((result) => {
        if (result) {
            this.registrationFormsDtos = this.registrationFormsDtos.filter((registrationFormDto) => registrationFormDto.user.id !== id);
            this.listProfiles(null);
        }
    });
  }

  openGraduateModal(id: number) {
    const modalRef = this.modalService.open(GraduateDialogComponent, {
        size: 'sm',
        centered: true,
        backdrop: 'static',
    });

    modalRef.componentInstance.id = id;
    modalRef.componentInstance.data = { id };

    modalRef.result.then((result) => {
        if (result) {
            this.registrationFormsDtos = this.registrationFormsDtos.filter((registrationFormDto) => registrationFormDto.user.id !== id);
            this.listProfiles(null);
        }
    });
  }

  getPages(): number[] {
    const totalPages = Math.ceil(this.dataLength / this.pageSize);
    return Array.from({ length: totalPages }, (_, i) => i);
  }

  showPhoneNumbers(phoneNumbers: string[]) {
    const hiddenNumbersDiv = document.getElementById('hiddenNumbers') as HTMLDivElement;
    hiddenNumbersDiv.innerHTML = '';
    for (let i = 1; i < phoneNumbers.length; i++) {
        hiddenNumbersDiv.innerHTML += phoneNumbers[i] + '<br>';
    }
    hiddenNumbersDiv.style.display = (hiddenNumbersDiv.style.display === 'block') ? 'none' : 'block';
  }

  openShowPhonesModal(phoneNumbers: string[]) {
    const modalRef = this.modalService.open(PhonesDialogComponent, {
        size: 'sm',
        centered: true,
        backdrop: 'static',
    });

    modalRef.componentInstance.data = { phoneNumbers };

  }
}
