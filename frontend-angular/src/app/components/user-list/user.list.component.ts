import { AfterViewInit, Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { UserOutputDto } from 'src/app/common/user.output.dto';
import { UsersReadResponse } from 'src/app/common/users.read.response';
import { UserService } from 'src/app/services/user.service';
import { EvaluateDialogUserComponent } from '../evaluate-dialog-user/evaluate.dialog.user.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user.list.component.html',
  styleUrls: ['./user.list.component.css'],
})
export class UserListComponent implements AfterViewInit {
  users: UserOutputDto[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';

  constructor(
    private userService: UserService,
    private modalService: NgbModal,
    private toastrService: ToastrService
  ) {}

  ngAfterViewInit() {
    this.listUsers(null);
  }

  listUsers(event?: any) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }
    this.getUserListPaginate(null,
      this.pageIndex,
      this.pageSize,
      this.sortField,
      this.sortDirection);
  }

  private getUserListPaginate(searchedContent: string, pageIndex: number, pageSize: number,
    sortField: string, sortDirection: string) {
    this.userService
      .getUserListPaginate(
        searchedContent,
        pageIndex,
        pageSize,
        sortField,
        sortDirection
      )
      .subscribe({
        next: (response: UsersReadResponse) => {
          this.users = response.content;
          this.dataLength = response.totalElements;
          this.pageIndex = response.pageable.pageNumber;
          this.pageSize = response.pageable.pageSize;
        },
        error: err => {
          this.toastrService.error(err.error.message, err.error.error);
        }
        
      });
  }

  search(event: HTMLInputElement) {
    const searchedContent = event.value;
    this.getUserListPaginate(searchedContent, this.pageIndex, this.pageSize, this.sortField, this.sortDirection);
  }

  onSortChange(sortField: string) {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.sortField = sortField;
    this.listUsers(null);
  }

  openEvaluationModal(id: number) {
    const modalRef = this.modalService.open(EvaluateDialogUserComponent, {
        size: 'sm',
        centered: true,
        backdrop: 'static',
    });

    modalRef.componentInstance.id = id;
    modalRef.componentInstance.data = { id };

    modalRef.result.then((result) => {
        if (result) {
            this.users = this.users.filter((user) => user.id !== id);
            this.listUsers(null);
        }
    });
  }

  getPages(): number[] {
    const totalPages = Math.ceil(this.dataLength / this.pageSize);
    return Array.from({ length: totalPages }, (_, i) => i);
  }
}
