import { Injectable } from '@angular/core';
import fetchFromAPI from './api';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorService } from './error.service';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from './auth.service';

const DEFAULT_USER: User = {
  id: 0,
  credentials: {
    username: "",
    password: ""
  },
  profile: {
    firstName: "",
    lastName: "",
    email: "",
    phone: ""
  },
  admin: false,
  active: false,
  status: '',
  companies: [],
  teams: []

}

const DEFAULT_COMPANY: Company = {
  id: 0,
  name: "",
  description: "",
  teams: [],
  users: []
}

// const dummyUser: User = {
//   id: 1,
//   credentials: {
//     username: "cousingreg",
//     password: "mosteligiblebachelor"
//   },
//   profile: {
//     firstName: "Greg",
//     lastName: "Hirsch",
//     email: "ghirsch@email.com",
//     phone: "000-000-0000"
//   },

//   admin: true,
//   active: false,
//   status: '',
//   companies: [
//       {
//           id: 1,
//           name: "Tech Corp",
//           description: "A leading tech company.",
//           teams: [
//               { id: 1, name: "Engineering", description: "Builds the main products.", users: [] },
//               { id: 2, name: "Design", description: "Designs interfaces and experiences.", users: [] }
//           ],
//           users: [] 
//       },
//       {
//           id: 2,
//           name: "Health Inc",
//           description: "Revolutionizing health solutions.",
//           teams: [
//               { id: 3, name: "Research", description: "Discovers new health solutions.", users: [] },
//               { id: 4, name: "Support", description: "Assists customers with their issues.", users: [] }
//           ],
//           users: []
//       },
//       {
//           id: 3,
//           name: "EcoSolutions",
//           description: "Environmentally friendly products.",
//           teams: [
//               { id: 5, name: "Product", description: "Designs eco-friendly products.", users: [] },
//               { id: 6, name: "Marketing", description: "Promotes the products.", users: [] }
//           ],
//           users: []
//       }
//   ],
//   teams: [
//       { id: 1, name: "Engineering", description: "Builds the main products.", users: [] },
//       { id: 3, name: "Research", description: "Discovers new health solutions.", users: [] },
//       { id: 5, name: "Product", description: "Designs eco-friendly products.", users: [] }
//   ]
// }
// const dummyCompany: Company = {
//   id: 6,
//   name: "EcoSolutions",
//   description: "Environmentally friendly products.",
//   teams: [
//       { id: 5, name: "Product", description: "Designs eco-friendly products.", users: [] },
//       { id: 6, name: "Marketing", description: "Promotes the products.", users: [] }
//   ],
//   users: []
// }

@Injectable({
  providedIn: 'root'
})

export class UserService {


  user: User = DEFAULT_USER
  company: Company = DEFAULT_COMPANY
  companyID : number = 0;
  username : string = "";
  password : string = "";
  admin : boolean = false;
  
  team: Team | undefined;
  project: Project | undefined;

  constructor(private errorService: ErrorService, private cookieService: CookieService) {}

  setUser(user: any, username : string, password : string){
    this.user = user;
    if (!user.admin) {
      this.company = user['companies'][0];
      this.companyID = user['companies'][0]['id'];
      this.cookieService.set("companyId", this.companyID.toString());
    }
    this.username = username;
    this.password = password;
    this.admin = user['admin'];

    this.cookieService.set("companyId", this.companyID.toString(), undefined, "/");
    this.cookieService.set("username", username.toString(), undefined, "/");
  }

  getUser() {
    return this.user;
  }

  setCompany(companyId: number) {
    // TODO: fix this    
    // using == to allow for string comparison
    const company = this.user.companies?.find(company => company.id == companyId);
    if (!company) {
      throw new Error('Company not found');
    }
    this.company = company;
    this.companyID = companyId;

    this.cookieService.set("companyId", this.companyID.toString(), undefined, "/");
  }

  getCompany() {
    return this.company;
  }

  async getSortedAnnouncements(): Promise<DisplayAnnouncement[]> {
    const endpoint = `company/${this.companyID}/announcements`;
    const response: DisplayAnnouncement[] = await fetchFromAPI("GET", endpoint);
    return response.sort((a, b) => {
      return new Date(b.date).getTime() - new Date(a.date).getTime();
    });;
  }
  async createNewAnnouncement(announcementToCreate: NewAnnouncement) {
    announcementToCreate.author = {
      profile : {
        firstName : "",
        lastName: "",
        phone: "",
        email: ""
      },
      credentials: {
        username: this.username,
        password: this.password
      },
      admin: this.getUser().admin,
      active: this.getUser().active,
      status: this.getUser().status
    }
    if(this.user.admin){
      announcementToCreate.companyName = this.company?.name;
    }
    const response: DisplayAnnouncement = await fetchFromAPI('POST', 'announcements/add', announcementToCreate)
    console.log(`Announcement created with id: ${response.id}`) 
  }

  async patchAnnouncement(id: number, announcementToUpdate: NewAnnouncement) {
    announcementToUpdate.author = {
      profile : {
        firstName : "",
        lastName: "",
        phone: "",
        email: ""
      },
      credentials: {
        username: this.username,
        password: this.password
      },
      admin: this.getUser().admin,
      active: this.getUser().active,
      status: this.getUser().status
    }
    const response: DisplayAnnouncement = await fetchFromAPI('PATCH', `announcements/update/${id}`, announcementToUpdate)
    console.log(`Announcement updated with id: ${response.id}`) 
  }

  async getProjectsByTeam(id : number) {
    const endpoint = `company/${this.company?.id}/teams/${id}/projects/team`;
    const response = await fetchFromAPI('GET', endpoint);
    return response;
  }

  async createNewProject(projectToCreate: Project) {
    const endpoint = `company/${this.company?.id}/teams/${projectToCreate.team.id}/projects`;
    const response = await fetchFromAPI('POST', endpoint, projectToCreate);
    return response;
  }

  async editProject(editedProject: Project) {
    const endpoint = `company/${this.company?.id}/teams/${editedProject.team.id}/projects/${editedProject.id}`;
    const response = await fetchFromAPI('PATCH', endpoint, editedProject);
    return response;
  }

  async getTeamById(id : number) {
    const endpoint = `teams/${id}`;
    const response = await fetchFromAPI('GET', endpoint);
    return response;
  }

  async getUsersFromCompany(id : number): Promise<User[]> {
    const endpoint = `company/${id}/users`;
    const response = await fetchFromAPI('GET', endpoint);
    return response;
  }

  async addUser(user : NewUser) {
    const endpoint = `users/new`;
    await fetchFromAPI('POST', endpoint, user);
  }

  async addUserToCompany(companyID: number, email: string) {
    const endpoint = `company/${companyID}/users/${email}`
    await fetchFromAPI("POST", endpoint)
  }

  async getCompanyTeams() {
    const endpoint = `company/${this.companyID}/teams`;
    const response = await fetchFromAPI('GET', endpoint);
    return response;
  }

  async getProjectsFromTeam(teamId: number) {
    const endpoint = `company/${this.companyID}/teams/${teamId}/projects/team`;
    const response = await fetchFromAPI('GET', endpoint);
    return response;
  }
  
  private handleHttpError(error: HttpErrorResponse) {
    if (error.status === 403) {
      // Handle rate limit exceeded error
      const customError = {
        error: 'API rate limit exceeded',
        tips: 'Please try again later.',
      };
      this.errorService.setError(customError);
    } else if (error.status === 404) {
      // Handle not found error
      const customError = {
        error: 'Not found',
        tips: 'The user was not found.',
      };
      this.errorService.setError(customError);
    } else {
      // Handle other HTTP errors
      const customError = {
        error: 'An error occurred',
        tips: 'Please try again later.',
      };
      this.errorService.setError(customError);
    }
  }
}