import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.css']
})
export class SearchInputComponent {
  selectedStatus: string = '';
  
  @Input() statuses: { name: string, value: string }[] = [];

  @Output() search: EventEmitter<any> = new EventEmitter();

  constructor() { }

  onSearch(searchInputValue: string, selectedStatus: string): void {
    this.search.emit({ searchInputValue, selectedStatus });
  }
}