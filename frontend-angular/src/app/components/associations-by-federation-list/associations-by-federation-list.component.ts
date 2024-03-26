import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AssociationOutputDto } from 'src/app/common/association.output.dto';
import { AssociationsResponse } from 'src/app/common/associations.response';
import { OrganizationStatusEnum } from 'src/app/common/organization.status.enum';
import { AssociationService } from 'src/app/services/association.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { DetailsDialogAssociationComponent } from '../details-dialog-association/details-dialog-association.component';
import { PhonesDialogComponent } from '../phones-dialog/phones-dialog.component';

@Component({
  selector: 'app-associations-by-federation-list',
  templateUrl: './associations-by-federation-list.component.html',
  styleUrls: ['./associations-by-federation-list.component.css']
})
export class AssociationsByFederationListComponent implements AfterViewInit, OnInit {
  statuses: { name: string, value: string }[] = Object.entries(OrganizationStatusEnum).map(([value, status]) => ({ name: status.name, value }));

  federationId: number = null;
  associationId: number = null;
  associationsOutputsDtos: AssociationOutputDto[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';
  searchedContent: string = '';
  selectedStatus: string = '';

  constructor(
    private associationService: AssociationService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe((param) => {
      this.federationId = Number(param.get('federationId'));
    });

  }

  ngAfterViewInit() {
    this.listAssociationsByFederation(null);
  }

  listAssociationsByFederation(event?: any) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }
    this.getPagedAssociationsDtos(this.federationId, this.searchedContent, this.selectedStatus,
      this.pageIndex,
      this.pageSize,
      this.sortField,
      this.sortDirection);
  }

  search(searchParams: { searchInputValue: string, selectedStatus: string }): void {
    this.searchedContent = searchParams.searchInputValue;
    this.selectedStatus = searchParams.selectedStatus;
    this.getPagedAssociationsDtos(this.federationId, this.searchedContent, this.selectedStatus,
        this.pageIndex, this.pageSize, this.sortField, this.sortDirection);
  }

  private getPagedAssociationsDtos(federationId: number, searchedContent: string | null, selectedStatus: string | null, pageIndex: number, pageSize: number, sortField: string, sortDirection: string) {
    this.associationService.getPagedAssociationsDtos(federationId, searchedContent, selectedStatus, pageIndex, pageSize, sortField, sortDirection)
      .subscribe({
        next: (response: AssociationsResponse) => {
          this.associationsOutputsDtos = response.content;
          this.dataLength = response.totalElements;
          this.pageIndex = response.totalElements % response.pageable.pageSize == 0 ?
            (response.totalElements / response.pageable.pageSize) - 1 :
            (response.totalElements / response.pageable.pageSize);
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
    this.getPagedAssociationsDtos(this.federationId, this.searchedContent, this.selectedStatus,
      this.pageIndex, this.pageSize, this.sortField, this.sortDirection);
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

  openAssociationDetailsModal(associationOutputDto: AssociationOutputDto) {
    const modalRef = this.modalService.open(DetailsDialogAssociationComponent, {
        size: 'sm',
        centered: true
    });
    modalRef.componentInstance.data = associationOutputDto;
  }

  hasPrivileges(uri: string): boolean {
    return this.authenticationService.hasPrivileges(uri);
  }
  
}
