import { RouteItem } from './route.item';
import { UserRoleEnum } from './user.role.enum';

export const endpoints = {
    routes: [
        {
            path: '/home',
            text: 'Página Inicial',
            authorities: Object.keys(UserRoleEnum).map(key => `${key}`),
            isMenu: true
        },
        {
            path: '/usuarios',
            text: 'Usuários',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR'],
            isMenu: true
        },
        {
            path: '/federacoes',
            text: 'Federações',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: true
        },
        {
            path: '/federacoes/:id/associacoes',
            text: 'Associações',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: false
        },
        {
            path: '/torneios',
            text: 'Torneios',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER'],
            isMenu: true
        },
        {
            path: '/torneios/criar',
            text: 'Criação de Torneios',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: false
        },
        {
            path: '/associacoes/criar',
            text: 'Criação de Associação',
            authorities: ['ROLE_ROOT'],
            isMenu: false
        },
        {
            path: '/federacoes/criar',
            text: 'Criação de Torneios',
            authorities: ['ROLE_ROOT'],
            isMenu: false
        },
        {
            path: '/torneios/:id/atualizar',
            text: 'Atualização de Torneio',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: false
        },
        {
            path: '/federacoes/:id/atualizar',
            text: 'Atualização de Federação',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: false
        },
        {
            path: '/associacoes/:id/atualizar',
            text: 'Atualização de Associação',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: false
        },
        {
            path: '/fichas',
            text: 'Fichas',
            authorities: ['ROLE_ROOT', 'ROLE_ADMIN'],
            isMenu: true
        },
        {
            path: '/fichas/:id/atualizar',
            text: 'Atualização de Ficha',
            authorities: Object.keys(UserRoleEnum).map(key => `${key}`),
            isMenu: false
        },
    ].map((route, index) => new RouteItem(index + 1, route.path, route.text, route.authorities, route.isMenu))
}