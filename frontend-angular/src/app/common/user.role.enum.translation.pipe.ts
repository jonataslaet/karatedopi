import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'userRoleEnumTranslation',
})
export class UserRoleEnumTranslationPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'ROLE_ROOT':
        return 'Root';
      case 'ROLE_ADMIN':
        return 'Administrador';
      case 'ROLE_MODERATOR':
        return 'Moderador';
      case 'ROLE_USER':
        return 'Usuário';
      default:
        return value;
    }
  }
}
