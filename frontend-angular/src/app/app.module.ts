import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BeltEnumTranslationPipe } from './common/belt-enum-translation-pipe';
import { EnumTranslationPipe } from './common/enum-translation-pipe';
import { AddressListComponent } from './components/address-list/address-list.component';
import { CreateRegistrationComponent } from './components/create-registration/create-registration.component';
import { DeleteDialogProfileComponent } from './components/delete-dialog-profile/delete-dialog-profile.component';
import { EvaluateDialogUserComponent } from './components/evaluate-dialog-user/evaluate-dialog-user.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { ParticipateDialogTournamentComponent } from './components/participate-dialog-tournament/participate-dialog-tournament.component';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { TournamentsListComponent } from './components/tournaments-list/tournaments-list.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { appConfiguration } from './configurations/app.configuration';
import { errorsHandlerConfiguration } from './configurations/errors-handler.configuration';
import { MaterialModule } from './material-module';
import { CityService } from './services/city-service';
import { ProfileService } from './services/profile.service';
import { RegistrationService } from './services/registration.service';
import { StateService } from './services/state-service';
import { TournamentService } from './services/tournament.service';

export function tokenGetter() {
  return sessionStorage.getItem("auth_token");
}

@NgModule({
  declarations: [
    AppComponent,
    ProfileListComponent,
    UpdateProfileComponent,
    DeleteDialogProfileComponent,
    CreateRegistrationComponent,
    TournamentsListComponent,
    EnumTranslationPipe,
    BeltEnumTranslationPipe,
    HeaderComponent,
    LoginFormComponent,
    HomeComponent,
    UserListComponent,
    EvaluateDialogUserComponent,
    AddressListComponent,
    ParticipateDialogTournamentComponent
  ],
  imports: [
    HttpClientModule,
    MaterialModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatDialogModule,
    NgxMaskDirective,
    NgxMaskPipe,
    ReactiveFormsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ["localhost:8080", "localhost:4200", "localhost:3000"],  //add all your allowed domain
        disallowedRoutes: [],
      },
    }),
  ],
  exports: [EnumTranslationPipe],
  providers: [appConfiguration.providers, errorsHandlerConfiguration.providers, TournamentService, RegistrationService, ProfileService, CityService, StateService, provideNgxMask()],
  bootstrap: [AppComponent]
})
export class AppModule {}
