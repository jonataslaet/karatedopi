import { AfterViewInit, Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { FederationOutputDto } from 'src/app/common/federation.output.dto';
import { FederationsResponse } from 'src/app/common/federations.response';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { FederationService } from 'src/app/services/federation.service';
import { DetailsDialogFederationComponent } from '../details-dialog-federation/details-dialog-federation.component';
import { PhonesDialogComponent } from '../phones-dialog/phones-dialog.component';

@Component({
  selector: 'app-federations-list',
  templateUrl: './federations-list.component.html',
  styleUrls: ['./federations-list.component.css']
})
export class FederationsListComponent implements AfterViewInit {
  federationsOutputsDtos: FederationOutputDto[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';

  constructor(
    private federationService: FederationService,
    private modalService: NgbModal,
    private authenticationService: AuthenticationService,
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
    this.getPagedFederationsDtos(
      null,
      this.pageIndex,
      this.pageSize,
      this.sortField,
      this.sortDirection
    );
  }

  search(event: HTMLInputElement) {
    const searchedContent = event.value;
    this.getPagedFederationsDtos(
      searchedContent,
      this.pageIndex,
      this.pageSize,
      this.sortField,
      this.sortDirection
    );
  }

  private getPagedFederationsDtos(
    searchedContent: string,
    page: number,
    pageSize: number,
    sortField: string,
    sortDirection: string) {
    this.federationService
      .getPagedFederationsDtos(
        searchedContent,
        page,
        pageSize,
        sortField,
        sortDirection
      )
      .subscribe({
        next: (response: FederationsResponse) => {
          this.federationsOutputsDtos = response.content;
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

  getPages(): number[] {
    const totalPages = Math.ceil(this.dataLength / this.pageSize);
    return Array.from({ length: totalPages }, (_, i) => i);
  }

  openShowPhonesModal(phoneNumbers: string[]) {
    const modalRef = this.modalService.open(PhonesDialogComponent, {
        size: 'sm',
        centered: true,
        backdrop: 'static',
    });

    modalRef.componentInstance.data = { phoneNumbers };

  }

  openFederationDetailsModal(federationOutputDto: FederationOutputDto) {
    const modalRef = this.modalService.open(DetailsDialogFederationComponent, {
        size: 'sm',
        centered: true
    });
    modalRef.componentInstance.data = federationOutputDto;
  }

  hasPrivileges(uri: string): boolean {
    return this.authenticationService.hasPrivileges(uri);
  }
  
}
