import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'enumTranslation',
})
export class EnumTranslationPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'OPENED':
        return 'Aberto';
      default:
        return value;
    }
  }
}
