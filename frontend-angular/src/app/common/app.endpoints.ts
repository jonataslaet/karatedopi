import { RouteItem } from './route-item';
import { UserRoleEnum } from './user-role-enum';

export const endpoints = {
    routes: [
        {
            path: '/home',
            text: 'Página Inicial',
            authorities: Object.keys(UserRoleEnum).map(key => `${key}`),
            isMenu: true
        },
        {
            path: '/addresses',
            text: 'Endereços',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: true
        },
        {
            path: '/profiles',
            text: 'Fichas',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: true
        },
        {
            path: '/tournaments',
            text: 'Torneios',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER'],
            isMenu: true
        },
        {
            path: '/tournaments/create',
            text: 'Criação de Torneios',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: false
        },
        {
            path: '/users',
            text: 'Usuários',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR'],
            isMenu: true
        }
    ].map((route, index) => new RouteItem(index + 1, route.path, route.text, route.authorities, route.isMenu))
}