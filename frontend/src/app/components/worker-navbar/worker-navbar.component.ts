import { Component } from '@angular/core';
import { AuthService } from 'src/services/auth.service';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-worker-navbar',
  templateUrl: './worker-navbar.component.html',
  styleUrls: ['./worker-navbar.component.css']
})
export class WorkerNavbarComponent {
  constructor(private authService: AuthService, private userService: UserService) {}

  userProfile!: UserProfile

  ngOnInit(): void {
    //if(localStorage.getItem('ROLE') == 'ADMIN') this.showAdminMsg = true
    this.userProfile = this.userService.getUser().profile
  }

  public logout() {
    this.authService.logout()
  }

}
