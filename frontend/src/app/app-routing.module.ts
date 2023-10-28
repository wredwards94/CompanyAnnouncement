import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CompanyComponent } from './components/company/company.component';
import { LoginComponent } from './components/login/login.component';
import { AnnouncementsComponent } from './components/announcements/announcements.component';
import { TeamsComponent } from './components/teams/teams.component';
import { UserRegistryComponent } from './components/user-registry/user-registry.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { authGuard } from 'src/services/auth.guard';

const routes: Routes = [
  {
    path: "", component: LoginComponent
  },
  {
    path: "select_company", component: CompanyComponent, canActivate: [authGuard], data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: "teams", component: TeamsComponent, canActivate: [authGuard]
  },
  {
    path: "announcements", component: AnnouncementsComponent, canActivate: [authGuard]
  },
  {
    path: "userRegistry", component: UserRegistryComponent, canActivate: [authGuard], data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: "projects/:teamid", component: ProjectsComponent, canActivate: [authGuard]
  },
  { path: '**', component: LoginComponent }
]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
