import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material-module';
import { ProfileListComponent } from './components/profile-list/profile-list/profile-list.component';
import { RouterModule, Routes } from '@angular/router';
import { ProfileService } from './services/profile.service';
import { MatSortModule } from '@angular/material/sort';

const routes: Routes = [
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
    ProfileListComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    HttpClientModule,
    MaterialModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [ProfileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
