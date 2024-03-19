import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.css']
})
export class SearchInputComponent {
  @Output() search: EventEmitter<HTMLInputElement> = new EventEmitter();

  constructor() { }

  onSearch(searchInput: HTMLInputElement) {
    this.search.emit(searchInput);
  }
}