import { MenuItem } from './menu-item';
import { UserRoleEnum } from './user-role-enum';

export const endpoints = {
    menus: [
        {
            path: '/home',
            text: 'Página Inicial',
            authorities: Object.keys(UserRoleEnum).map(key => `${key}`)
        },
        {
            path: '/addresses',
            text: 'Endereços',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN']
        },
        {
            path: '/profiles',
            text: 'Fichas',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN']
        },
        {
            path: '/tournaments',
            text: 'Torneios',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER']
        },
        {
            path: '/users',
            text: 'Usuários',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR']
        }
    ].map((menu, index) => new MenuItem(index + 1, menu.path, menu.text, menu.authorities))
}