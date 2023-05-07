import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material-module';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { RouterModule, Routes } from '@angular/router';
import { ProfileService } from './services/profile.service';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DeleteDialogProfileComponent } from './components/delete-dialog-profile/delete-dialog-profile.component';
import { MatDialogModule } from '@angular/material/dialog';
import { CreateRegistrationComponent } from './components/create-registration/create-registration.component';
import { RegistrationService } from './services/registration.service';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { CityService } from './services/city-service';
import { StateService } from './services/state-service';

const routes: Routes = [
  {
    path:'registration',
    component: CreateRegistrationComponent
  },
  {
    path:'profile/update/:id', component: UpdateProfileComponent
  },
  {
    path: 'profiles', component: ProfileListComponent
  },
  {
    path: '', redirectTo: '/profiles', pathMatch: 'full'
  },
  {
    path: '**', redirectTo: '/profiles', pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    ProfileListComponent,
    UpdateProfileComponent,
    DeleteDialogProfileComponent,
    CreateRegistrationComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    HttpClientModule,
    MaterialModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatDialogModule,
    NgxMaskDirective,
    NgxMaskPipe,
    ReactiveFormsModule
  ],
  providers: [RegistrationService, ProfileService, CityService, StateService, provideNgxMask()],
  bootstrap: [AppComponent]
})
export class AppModule { }
