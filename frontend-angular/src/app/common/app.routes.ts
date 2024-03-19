import { Routes } from '@angular/router';
import { AssociationCreateComponent } from '../components/association-create/association-create.component';
import { AssociationUpdateComponent } from '../components/association-update/association-update.component';
import { AssociationsByFederationListComponent } from '../components/associations-by-federation-list/associations-by-federation-list.component';
import { FederationCreateComponent } from '../components/federation-create/federation-create.component';
import { FederationUpdateComponent } from '../components/federation-update/federation-update.component';
import { FederationsListComponent } from '../components/federations-list/federations-list.component';
import { HomeComponent } from '../components/home/home.component';
import { LoginFormComponent } from '../components/login-form/login-form.component';
import { RecoveryTokenComponent } from '../components/recovery-token/recovery-token.component';
import { RegistrationFormCreateComponent } from '../components/registration-form-create/registration-form-create.component';
import { RegistrationFormListComponent } from '../components/registration-form-list/registration-form-list.component';
import { RegistrationFormUpdateComponent } from '../components/registration-form-update/registration.form.update.component';
import { RenewPasswordComponent } from '../components/renew-password/renew-password.component';
import { TournamentCreateComponent } from '../components/tournament-create/tournament-create.component';
import { TournamentUpdateComponent } from '../components/tournament-update/tournament-update.component';
import { TournamentsListComponent } from '../components/tournaments-list/tournaments-list.component';
import { UserListComponent } from '../components/user-list/user.list.component';
import { AuthenticationGuard } from '../services/authentication.guard';
import { LoginGuard } from '../services/login.guard';

export const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'senhas/renovar/:token',
        component: RenewPasswordComponent
    },
    {
        path: 'federacoes/:federationId/associacoes',
        component: AssociationsByFederationListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'fichas/criar',
        component: RegistrationFormCreateComponent,
    },
    {
        path: 'recupera-token',
        component: RecoveryTokenComponent,
    },
    {
        path: 'login',
        component: LoginFormComponent,
        canActivate: [LoginGuard]
    },
    {
        path: 'usuarios',
        component: UserListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'federacoes',
        component: FederationsListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'torneios',
        component: TournamentsListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'torneios/criar',
        component: TournamentCreateComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'federacoes/criar',
        component: FederationCreateComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'associacoes/criar',
        component: AssociationCreateComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'torneios/:id/atualizar',
        component: TournamentUpdateComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'associacoes/:id/atualizar',
        component: AssociationUpdateComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'federacoes/:id/atualizar',
        component: FederationUpdateComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'fichas/:id/atualizar',
        component: RegistrationFormUpdateComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'fichas',
        component: RegistrationFormListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full',
    },
    {
        path: '**',
        redirectTo: '/login',
        pathMatch: 'full',
    },
];
