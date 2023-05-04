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
import { FormsModule } from '@angular/forms';
import { CreateProfileComponent } from './components/create-profile/create-profile.component';
import { DeleteDialogProfileComponent } from './components/delete-dialog-profile/delete-dialog-profile.component';
import { MatDialogModule } from '@angular/material/dialog';

const routes: Routes = [,
  {
    path:'profile/create',
    component: CreateProfileComponent
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

// registerLocaleData(localePt, 'pt-BR');

@NgModule({
  declarations: [
    AppComponent,
    ProfileListComponent,
    UpdateProfileComponent,
    CreateProfileComponent,
    DeleteDialogProfileComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    HttpClientModule,
    MaterialModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatDialogModule
  ],
  providers: [ProfileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
