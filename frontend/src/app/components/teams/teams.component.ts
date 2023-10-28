import { Component } from '@angular/core';
import { UserService } from 'src/services/user.service';
import { AuthService } from 'src/services/auth.service';

interface Team {
  id: number,
  name: string,
  description: string,
  teammates: User[],
  numberOfProjects?: number
}


@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {

  userData: any = {};
  teamData: Team[] | undefined;
  isUserAdmin: boolean = false;
  user: User;

  constructor(private userService : UserService, private authService: AuthService) {
    this.user = this.userService.getUser();
  }

  showModal: boolean = false;

  async ngOnInit(): Promise<void> {
    if (this.userService.username === "") {
      await this.authService.cookieCall();
    }

    this.user = this.userService.getUser();

    this.isUserAdmin = this.user.admin;
    await this.fetchTeams();
  }

  async getNumberOfProjects(teamId: number): Promise<number> {
    try {
      const response = await this.userService.getProjectsFromTeam(teamId)
      return response.length;
    } catch (error) {
      console.error(`Error fetching projects for Team ${teamId}:`, error);
      // Return 0 projects in case of an error
    }
    return 0;
  }


  openModal() {
    this.showModal = true;
  }

  async closeModal() {
    await this.fetchTeams();
    this.showModal = false;
  }

  async fetchTeams() {
    
    this.teamData = await this.userService.getCompanyTeams()
    if (this.teamData !== undefined) {
      this.teamData = this.teamData.sort((a, b) => {
        return (a.name.toUpperCase() < b.name.toUpperCase()) ? -1 : (a.name.toUpperCase() > b.name.toUpperCase()) ? 1 : 0
      });;
      if (!this.isUserAdmin) {
        this.teamData = this.teamData.filter(team =>
          team.teammates.find(teammate => teammate.id === this.user.id)
        );
      }
    }
    if (this.teamData) {
      for (const team of this.teamData) {
        team.numberOfProjects = await this.getNumberOfProjects(team.id);
      }
    }
  }

  async fetchWorkerTeams() {
    this.teamData = await this.userService.getCompanyTeams()
    if (this.teamData !== undefined) {
      this.teamData = this.teamData.filter(team =>
        team.teammates.some(teammate => teammate.id === this.userService.user.id)
      );

      if (this.teamData) {
        for (const team of this.teamData) {
          team.numberOfProjects = await this.getNumberOfProjects(team.id);
        }
      }

      this.teamData.sort((a, b) => {
        return (a.name < b.name) ? -1 : (a.name > b.name) ? 1 : 0
      });;
    }
  }

}

