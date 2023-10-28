import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})

export class ProjectsComponent implements OnInit{
  showEdit: boolean = false;
  showNew: boolean = false;
  reloadProjects : boolean = false;
  accessDenied : boolean = true;
  projects: Project[] = [];
  selectedProject: Project | null = null;
  teamId: number = -1;
  team: Team = {
    id: -1,
    name: '',
    description: '',
    users: []
  }
  user: User;

  constructor(private route: ActivatedRoute, private userService: UserService, private authService: AuthService){
    this.user = this.userService.getUser();
  }

  async ngOnInit() {
    if (this.userService.username === "") {
      await this.authService.cookieCall();
    }

    this.user = this.userService.getUser();

    this.teamId = Number(this.route.snapshot.paramMap.get('teamid'));
    if (this.teamId != null) {
      // check if the team belongs to the current company
      if (this.user.admin) {
        if (this.user.companies != undefined) {
          let company = this.user.companies.find(c => c.id == this.userService.companyID);
          if (company != undefined) {
            let team = company.teams.find(t => t.id === this.teamId);
            if (team != undefined) {
              this.accessDenied = false;
            }
          }
        }
      }
      // if user is not an admin, check if the user belongs to this team
      else {
        if (this.user.teams != undefined) {
          let team = this.user.teams.find(t => t.id == this.teamId);
          if (team != undefined) {
            this.accessDenied = false;
          }
        }
      }

      // if user is an admin of a different company, or is not an admin and not a member of the team they are trying to view
      if (this.accessDenied) {
        alert("This user does not have permission to access this team.");
      }
      else {
        this.fetchTeam();
        this.fetchProjects();
      }
    }
  }

  getTeam(): Team {
    return this.team;
  }

  getProjects(): Project[] {
    return this.projects;
  }

  fetchTeam(): void {
    this.userService.getTeamById(this.teamId).then((result) => {
      this.team = result;
    });
  }

  fetchProjects(): void {
    console.log("getting projects");
    this.reloadProjects = true;
    this.userService.getProjectsByTeam(this.teamId).then((result) => {
      this.projects = result;
      this.reloadProjects = false;
    });
  }

  getSelectedProject(): Project {
    if (this.selectedProject !== null) {
      return this.selectedProject;
    }
    let newProject : Project = {
      "name": "",
      "description": "",
      "active": true,
      "team": this.team,
    }
    return newProject;
  }

  modalClosed(closeType : String) {
    console.log(closeType);
    this.showEdit = false;
    if (closeType !== "NONE") {
      this.fetchProjects();
    }
  }

  toggleEdit(project: Project | null) {
    if (project === null) {
      return;
    }
    this.selectedProject = project;
    this.showEdit = !this.showEdit;
    this.showNew = false;
  }
  toggleNew(){
    this.selectedProject = {
      id: -1,
      name: '',
      active: true,
      description: '',
      team: this.team
    }
    this.showNew = true;
    this.showEdit = true;
  }
}
