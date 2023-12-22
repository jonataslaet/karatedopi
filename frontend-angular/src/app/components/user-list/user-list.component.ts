import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { AuthenticationResponse } from 'src/app/common/authentication-response';
import { UserReadResponse } from 'src/app/common/user-read-response';
import { UsersReadResponse } from 'src/app/common/users-read-response';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import { EvaluateDialogUserComponent } from '../evaluate-dialog-user/evaluate-dialog-user.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements AfterViewInit, OnInit {
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
  
  constructor(private userService: UserService, public dialog: MatDialog,
    private authenticationService: AuthenticationService, private router: Router) {
    this.dataSource = new MatTableDataSource(this.users);
  }

  ngOnInit(): void {
    this.authenticationService.currentUserSignal();
    this.authenticationService.getAuthenticatedUser().subscribe({
      next: (response: AuthenticationResponse) => {
        this.authenticationService.currentUserSignal.set(response);
      },
      error: () => {
        localStorage.setItem('auth_token', '');
        this.authenticationService.currentUserSignal.set(null);
        this.router.navigate(['/login']);
      },
    });
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
