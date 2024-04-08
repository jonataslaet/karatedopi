import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BeltEnumTranslationPipe } from './common/belt.enum.translation.pipe';
import { StatusEnumTranslationPipe } from './common/status.enum.translation.pipe';
import { UserRoleEnumTranslationPipe } from './common/user.role.enum.translation.pipe';
import { AddressCreateComponent } from './components/address-create/address-create.component';
import { AssociationCreateComponent } from './components/association-create/association-create.component';
import { AssociationUpdateComponent } from './components/association-update/association-update.component';
import { AssociationsByFederationListComponent } from './components/associations-by-federation-list/associations-by-federation-list.component';
import { DeleteDialogProfileComponent } from './components/delete-dialog-profile/delete-dialog-profile.component';
import { DetailsDialogAssociationComponent } from './components/details-dialog-association/details-dialog-association.component';
import { DetailsDialogFederationComponent } from './components/details-dialog-federation/details-dialog-federation.component';
import { EvaluateDialogUserComponent } from './components/evaluate-dialog-user/evaluate.dialog.user.component';
import { FederationCreateComponent } from './components/federation-create/federation-create.component';
import { FederationUpdateComponent } from './components/federation-update/federation-update.component';
import { FederationsListComponent } from './components/federations-list/federations-list.component';
import { GraduateDialogComponent } from './components/graduate-dialog/graduate-dialog.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { ParticipateDialogTournamentComponent } from './components/participate-dialog-tournament/participate-dialog-tournament.component';
import { PhonesDialogComponent } from './components/phones-dialog/phones-dialog.component';
import { RecoveryTokenComponent } from './components/recovery-token/recovery-token.component';
import { RegistrationFormCreateComponent } from './components/registration-form-create/registration-form-create.component';
import { RegistrationFormListComponent } from './components/registration-form-list/registration-form-list.component';
import { RegistrationFormUpdateComponent } from './components/registration-form-update/registration.form.update.component';
import { RenewPasswordComponent } from './components/renew-password/renew-password.component';
import { TournamentCreateComponent } from './components/tournament-create/tournament-create.component';
import { TournamentUpdateComponent } from './components/tournament-update/tournament-update.component';
import { TournamentsListComponent } from './components/tournaments-list/tournaments-list.component';
import { UserListComponent } from './components/user-list/user.list.component';
import { appConfiguration } from './configurations/app.configuration';
import { SearchInputComponent } from './components/search-input/search-input.component';
import { LandingComponent } from './components/landing/landing.component';
export function tokenGetter() {
  return sessionStorage.getItem("auth_token");
}

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    HomeComponent,
    HeaderComponent,
    UserListComponent,
    EvaluateDialogUserComponent,
    ParticipateDialogTournamentComponent,
    TournamentsListComponent,
    StatusEnumTranslationPipe,
    UserRoleEnumTranslationPipe,
    DeleteDialogProfileComponent,
    BeltEnumTranslationPipe,
    TournamentUpdateComponent,
    RenewPasswordComponent,
    RecoveryTokenComponent,
    PhonesDialogComponent,
    RegistrationFormUpdateComponent,
    RegistrationFormListComponent,
    GraduateDialogComponent,
    FederationsListComponent,
    DetailsDialogFederationComponent,
    AssociationsByFederationListComponent,
    DetailsDialogAssociationComponent,
    FederationCreateComponent,
    FederationUpdateComponent,
    AssociationUpdateComponent,
    AssociationCreateComponent,
    AddressCreateComponent,
    RegistrationFormCreateComponent,
    TournamentCreateComponent,
    SearchInputComponent,
    LandingComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxMaskDirective,
    NgxMaskPipe,
    ReactiveFormsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ["localhost:8080", "localhost:4200", "localhost:3000", "karatedopi-api.sa-east-1.elasticbeanstalk.com:8080", "karatedopi-api.sa-east-1.elasticbeanstalk.com"],  //add all your allowed domain
        disallowedRoutes: [],
      },
    }),
    NgbModule,
  ],
  exports: [StatusEnumTranslationPipe],
  providers: [appConfiguration.providers, provideNgxMask(), {provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
