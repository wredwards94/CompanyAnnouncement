
import { Component, Input, Output, EventEmitter } from '@angular/core';
import fetchFromAPI from 'src/services/api';
import { UserService } from 'src/services/user.service';

const DEFAULT_ANNOUNCEMENT: NewAnnouncement = {   
  title: '',
  message: '',
  author: {
    id: 0, 
    profile: {
      firstName: '',
      lastName: '',
      email: '',
      phone: ''
    },
    credentials: {
      username: '',
      password: ''
    },
    admin: false,
    active: false,
    status: ''
  },
  companyName: ''
};

@Component({
  selector: 'app-announcement-modal',
  templateUrl: './announcement-modal.component.html',
  styleUrls: ['./announcement-modal.component.css']
})
export class AnnouncementModalComponent {
  
  @Input() modalOpen: boolean = false;
  @Input() announcementToEdit: DisplayAnnouncement | null = null;

  @Output() modalClosed = new EventEmitter<void>();

  announcementToCreate: NewAnnouncement = DEFAULT_ANNOUNCEMENT;
  
  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    if (this.announcementToEdit) {
        this.announcementToCreate.title = this.announcementToEdit.title;
        this.announcementToCreate.message = this.announcementToEdit.message;
    } else {
      this.announcementToCreate = {...DEFAULT_ANNOUNCEMENT}
    }
  }
  async handleNewAnnouncement(){
    if (this.announcementToEdit) {
      if (this.userService.getUser().admin) {
        this.announcementToCreate.companyName = this.userService.getCompany()?.name;
      }
      await this.userService.patchAnnouncement(this.announcementToEdit.id, this.announcementToCreate);
    } else {
      await this.userService.createNewAnnouncement(this.announcementToCreate);
    }
    this.closeModal();
  }

  closeModal(): void {
    this.announcementToEdit = null;
    this.announcementToCreate.title = '';
    this.announcementToCreate.message = '';
    this.modalClosed.emit();
  }
}
