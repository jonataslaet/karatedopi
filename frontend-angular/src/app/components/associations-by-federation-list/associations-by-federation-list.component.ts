import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AssociationOutputDto } from 'src/app/common/association.output.dto';
import { AssociationsResponse } from 'src/app/common/associations.response';
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

  federationId: number = null;
  associationId: number = null;
  associationsOutputsDtos: AssociationOutputDto[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';

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
    this.getPagedAssociationsDtos(this.federationId, null, this.pageIndex, this.pageSize, this.sortField, this.sortDirection);
  }

  search(event: HTMLInputElement) {
    const searchedContent = event.value;
    this.getPagedAssociationsDtos(this.federationId, searchedContent, this.pageIndex, this.pageSize, this.sortField, this.sortDirection);
  }

  private getPagedAssociationsDtos(federationId: number, searchedContent: string | null, pageIndex: number, pageSize: number, sortField: string, sortDirection: string) {
    this.associationService.getPagedAssociationsDtos(federationId, searchedContent, pageIndex, pageSize, sortField, sortDirection)
      .subscribe({
        next: (response: AssociationsResponse) => {
          this.associationsOutputsDtos = response.content;
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
    this.listAssociationsByFederation(null);
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
