import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'statusEnumTranslation',
})
export class StatusEnumTranslationPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'OPENED':
        return 'Aberto';
      case 'ACTIVE':
        return 'Ativo';
      case 'PENDING_EVALUATION':
        return 'Pendente de avaliação';
      case 'SUSPENDED':
        return 'Suspenso';
      case 'IN_PROGRESS':
        return 'Em andamento';
      case 'FINISHED':
        return 'Finalizado';
      default:
        return value;
    }
  }
}
