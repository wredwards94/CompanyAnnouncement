import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent {

  constructor(private userService: UserService, private router: Router) { }

  currentUser!: User;
  companySelected_id: number = -1;

  ngOnInit(): void {
    this.currentUser = this.userService.getUser()
  }

  selectCompany(): void {
    this.userService.setCompany(this.companySelected_id);
    if (this.companySelected_id !== -1) { 
      this.router.navigate(['/announcements']);
    }
  }
}
