import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { routes } from './common/app.routes';
import { EnumTranslationPipe } from './common/enum-translation-pipe';
import { CreateRegistrationComponent } from './components/create-registration/create-registration.component';
import { DeleteDialogProfileComponent } from './components/delete-dialog-profile/delete-dialog-profile.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { TournamentsListComponent } from './components/tournaments-list/tournaments-list.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { MaterialModule } from './material-module';
import { CityService } from './services/city-service';
import { ProfileService } from './services/profile.service';
import { RegistrationService } from './services/registration.service';
import { StateService } from './services/state-service';
import { TournamentService } from './services/tournament.service';


@NgModule({
  declarations: [
    AppComponent,
    ProfileListComponent,
    UpdateProfileComponent,
    DeleteDialogProfileComponent,
    CreateRegistrationComponent,
    TournamentsListComponent,
    EnumTranslationPipe,
    HeaderComponent,
    LoginFormComponent
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
  exports: [EnumTranslationPipe],
  providers: [TournamentService, RegistrationService, ProfileService, CityService, StateService, provideNgxMask()],
  bootstrap: [AppComponent]
})
export class AppModule {}
