import { Component, Output, EventEmitter, Input } from '@angular/core';
import fetchFromAPI from 'src/services/api';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/services/user.service';


interface Profile {
  firstName: string,
  lastName: string,
  email:string,
  phone: string
}

interface User {
    id: number,
    profile: Profile,
    isAdmin: boolean,
    active: boolean,
    status: string
  }

  interface Team {
    id: number,
    name: string,
    description: string,
    teammates: User[]
  }

@Component({
  selector: 'app-teams-modal',
  templateUrl: './teams-modal.component.html',
  styleUrls: ['./teams-modal.component.css']
})
export class TeamsModalComponent {

  teamData: Team[] | undefined;
  memberData: User[] | undefined;
  selectedMembers: User[] = [];
  teamName: string = '';
  teamDescription: string = '';
  teamForm: FormGroup;

  @Output() close = new EventEmitter<void>();
  @Input() showModal: boolean = false;

  constructor(private userService : UserService, private formBuilder: FormBuilder) {
    this.teamForm = this.formBuilder.group({
      teamName: ['', Validators.required],
      teamDescription: ['', Validators.required]
    })
  }


  async ngOnInit(): Promise<void> {
    this.memberData = await fetchFromAPI("GET", `company/${this.userService.companyID}/users`);
  }

  onClose() {
    this.close.emit();
  }

  onMemberSelect(event: any) {
    console.log("member selection triggered");
    const selectedId = Number(event.target.value);
    const selectedMember = this.memberData?.find(member => member.id === selectedId)
    if (selectedMember && this.selectedMembers.indexOf(selectedMember) === -1) {
      this.selectedMembers.push(selectedMember);
    }
  }

  onOutsideClick(event: MouseEvent) {
    if ((event.target as HTMLElement).classList.contains('modal-container')) {
        this.onClose();
    }
}

removeMember(member: User): void {
  console.log(this.selectedMembers + 'before')
  const index = this.selectedMembers.findIndex((m: User) => m.id === member.id);
  if (index > -1) {
      this.selectedMembers.splice(index, 1);
  }
}

async onSubmit() : Promise<void> {

  if (this.teamForm.valid) {
    const body = {
      name: this.teamForm.get('teamName')?.value,
      description: this.teamForm.get('teamDescription')?.value,
      teammateIds: this.selectedMembers.map(member => member.id),
      companyId: this.userService.companyID
    }
    this.teamData = await fetchFromAPI("POST", "teams", body);
    alert('Team created successfully');
    this.onClose();
  } else {
    console.error("Form is invalid");
  }

}

}
