import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CookieService} from 'ngx-cookie-service';
import { LoginComponent } from '../app/components/login/login.component';
import { CompanyComponent } from './components/company/company.component';
import { AnnouncementsComponent } from './components/announcements/announcements.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AnnouncementModalComponent } from './components/announcement-modal/announcement-modal.component';
import { TeamsComponent } from './components/teams/teams.component';
import { TeamsModalComponent } from './components/teams-modal/teams-modal.component';
import { Routes } from '@angular/router';
import { UserRegistryComponent } from './components/user-registry/user-registry.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { UserModalComponent } from './components/user-modal/user-modal.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ProjectsComponent } from './components/projects/projects.component';
import { EditProjectComponent } from './components/edit-project/edit-project.component';
import { WorkerNavbarComponent } from './components/worker-navbar/worker-navbar.component';

const routes: Routes = [
  { path: "", component: CompanyComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CompanyComponent,
    AnnouncementsComponent,
    AnnouncementModalComponent,
    NavbarComponent,
    TeamsComponent,
    TeamsModalComponent,
    UserRegistryComponent,
    AddUserComponent,
    UserModalComponent,
    ProjectsComponent,
    EditProjectComponent,
    WorkerNavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    HttpClientModule
  ],

  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
