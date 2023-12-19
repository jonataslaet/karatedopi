import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateRegistrationComponent } from './components/create-registration/create-registration.component';
import { HomeComponent } from './components/home/home.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { TournamentsListComponent } from './components/tournaments-list/tournaments-list.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';

const routes: Routes = [{
  path: 'home',
  component: HomeComponent,
},
{
  path: 'registration',
  component: CreateRegistrationComponent,
},
{
  path: 'profile/update/:id',
  component: UpdateProfileComponent,
},
{
  path: 'profiles',
  component: ProfileListComponent,
},
{
  path: 'tournaments',
  component: TournamentsListComponent,
},
{
  path: 'login',
  component: LoginFormComponent,
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
},];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
