import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UserReadResponse } from 'src/app/common/user-read-response';
import { UsersReadResponse } from 'src/app/common/users-read-response';
import { UserService } from 'src/app/services/user.service';
import { EvaluateDialogUserComponent } from '../evaluate-dialog-user/evaluate-dialog-user.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements AfterViewInit {
  users: UserReadResponse[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';
  
  displayedColumns: string[] = [
    'firstname', 'lastname', 'email', 'status', 'authority', 'actions'
  ];
  dataSource: MatTableDataSource<UserReadResponse>;

  @ViewChild(MatSort) sort: MatSort;
  
  constructor(private userService: UserService, public dialog: MatDialog) {
    this.dataSource = new MatTableDataSource(this.users);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.listUsers(null);
  }

  listUsers(event?:PageEvent) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }
    this.userService.getUserListPaginate(this.pageIndex, this.pageSize, this.sortField, this.sortDirection)
      .subscribe((response: UsersReadResponse) => {
        this.dataSource = new MatTableDataSource(response.content);
        this.users = response.content;
        this.dataLength = response.totalElements;
        this.pageIndex = response.pageable.pageNumber;
        this.pageSize = response.pageable.pageSize;
      });
  }

  search(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onSortChange(sortState: Sort) {
    this.sortDirection = sortState.direction;
    this.sortField = sortState.active;
    this.listUsers(null);
  }

  openEvaluationModal(id: number) {
    const dialogRef = this.dialog.open(EvaluateDialogUserComponent, {
      width: '250px',
      data: { id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.users = this.users.filter(
          (_) => _.id !== id
        );
        this.listUsers(null);
      }
    });
  }
}
