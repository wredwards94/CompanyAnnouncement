import { Component } from '@angular/core';
import fetchFromAPI from 'src/services/api';
import { AuthService } from 'src/services/auth.service';
import { UserService } from 'src/services/user.service';

const DEFAULT_USER: User = {
  profile: {
    firstName: '',
    lastName: '',
    email: '',
    phone: ''
  },
  credentials: {
    username: "testy",
    password: "test"
  },
  admin: false,
  active: false,
  status: ''
};

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})
export class AnnouncementsComponent {

    user: User = DEFAULT_USER;
    announcementsToDisplay: DisplayAnnouncement[] | undefined;
    company: Company | undefined;
    modalOpen = false;

    editingAnnouncement: DisplayAnnouncement | null = null;

  
    constructor(private userService: UserService, private authService: AuthService) { }
  
    async ngOnInit(): Promise<void> {
      if (this.userService.username === "") {
        await this.authService.cookieCall();
      }

      this.user = this.userService.getUser();
      this.announcementsToDisplay = await this.userService.getSortedAnnouncements()
    }
  
    openModal(): void {
      this.modalOpen = true;
    }

    openModalForEdit(announcement: DisplayAnnouncement): void {
      this.editingAnnouncement = announcement;
      this.openModal();
    }
    
    async modalWasClosed(): Promise<void> {
      this.modalOpen = false;
      this.announcementsToDisplay = await this.userService.getSortedAnnouncements()
    }

}
