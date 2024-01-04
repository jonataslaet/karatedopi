import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AddressReadResponse } from 'src/app/common/address-read-response';
import { AddressesReadResponse } from 'src/app/common/addresses-read-response';
import { AddressService } from 'src/app/services/address.service';

@Component({
  selector: 'app-address-list',
  templateUrl: './address-list.component.html',
  styleUrls: ['./address-list.component.css']
})
export class AddressListComponent implements AfterViewInit {
  addresses: AddressReadResponse[] = [];
  dataLength: number;
  pageIndex: number = 0;
  pageSize: number = 3;
  sortField: string = 'id';
  sortDirection: string = 'desc';
  
  displayedColumns: string[] = [
    'street', 'number', 'zipCode', 'neighbourhood', 'city', 'state'
  ];
  dataSource: MatTableDataSource<AddressReadResponse>;

  @ViewChild(MatSort) sort: MatSort;
  
  constructor(private addressService: AddressService,
    public dialog: MatDialog) {
    this.dataSource = new MatTableDataSource(this.addresses);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.listAddresses(null);
  }

  listAddresses(event?:PageEvent) {
    if (event != null) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
      this.dataLength = event.length;
    }
    this.addressService.getAddressListPaginate(this.pageIndex, this.pageSize, this.sortField, this.sortDirection)
      .subscribe((response: AddressesReadResponse) => {
        this.dataSource = new MatTableDataSource(response.content);
        this.addresses = response.content;
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
    this.listAddresses(null);
  }
}
