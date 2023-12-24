import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ProfileReadResponse } from 'src/app/common/profile-read-response';
import { ProfilesReadResponse } from 'src/app/common/profiles-read-response';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProfileService } from 'src/app/services/profile.service';
import { DeleteDialogProfileComponent } from '../delete-dialog-profile/delete-dialog-profile.component';

@Component({
  selector: 'app-profile-list',
  templateUrl: './profile-list.component.html',
  styleUrls: ['./profile-list.component.css']
})
export class ProfileListComponent implements AfterViewInit {
  profiles: ProfileReadResponse[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';
  
  displayedColumns: string[] = [
    'fullname', 'zipCode', 'neighbourhood', 'city', 'state', 'bloodType', 'birthday', 'cpf', 'rg', 'actions'
  ];
  dataSource: MatTableDataSource<ProfileReadResponse>;

  @ViewChild(MatSort) sort: MatSort;
  
  constructor(private profileService: ProfileService, public dialog: MatDialog, private authenticationService: AuthenticationService) {
    this.dataSource = new MatTableDataSource(this.profiles);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.listProfiles(null);
  }

  listProfiles(event?:PageEvent) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }
    this.profileService.getProfileListPaginate(this.pageIndex, this.pageSize, this.sortField, this.sortDirection)
      .subscribe((response: ProfilesReadResponse) => {
        this.dataSource = new MatTableDataSource(response.content);
        this.profiles = response.content;
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
    this.listProfiles(null);
  }

  openDeleteModal(id: number) {
    const dialogRef = this.dialog.open(DeleteDialogProfileComponent, {
      width: '250px',
      data: { id },
    });
 
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.profiles = this.profiles.filter(
          (_) => _.id !== id
        );
        this.listProfiles(null);
      }
    });
  }
}
