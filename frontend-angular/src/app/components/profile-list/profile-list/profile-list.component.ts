import { GetResponseProfiles } from 'src/app/common/get-response-profiles';
import { Profile } from 'src/app/common/profile';
import { ProfileService } from 'src/app/services/profile.service';
import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';
import {MatSort, Sort} from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';

@Component({
  selector: 'app-profile-list',
  templateUrl: './profile-list.component.html',
  styleUrls: ['./profile-list.component.css']
})
export class ProfileListComponent implements AfterViewInit {
  profiles: Profile[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';
  
  displayedColumns: string[] = [
    'id', 'firstname', 'lastname', 'father', 'mother', 'hometown', 'birthday', 'cpf', 'rg'
  ];
  dataSource: MatTableDataSource<Profile>;

  @ViewChild(MatSort) sort: MatSort;
  
  constructor(private profileService: ProfileService, private _liveAnnouncer: LiveAnnouncer) {
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
      .subscribe((response: GetResponseProfiles) => {
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
}
