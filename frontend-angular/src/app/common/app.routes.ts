import { Routes } from '@angular/router';
import { AddressListComponent } from '../components/address-list/address-list.component';
import { CreateRegistrationComponent } from '../components/create-registration/create-registration.component';
import { HomeComponent } from '../components/home/home.component';
import { LoginFormComponent } from '../components/login-form/login-form.component';
import { ProfileListComponent } from '../components/profile-list/profile-list.component';
import { TournamentsListComponent } from '../components/tournaments-list/tournaments-list.component';
import { UpdateProfileComponent } from '../components/update-profile/update-profile.component';
import { UserListComponent } from '../components/user-list/user-list.component';
import { AuthenticationGuard } from '../services/authentication-guard';

export const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'addresses',
        component: AddressListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'registration',
        component: CreateRegistrationComponent,
    },
    {
        path: 'profile/update/:id',
        component: UpdateProfileComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'profiles',
        component: ProfileListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'tournaments',
        component: TournamentsListComponent,
        canActivate: [AuthenticationGuard]
    },
    {
        path: 'login',
        component: LoginFormComponent
    },
    {
        path: 'users',
        component: UserListComponent,
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
