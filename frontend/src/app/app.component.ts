import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';

  constructor(private authService: AuthService, private router: Router) {}

  isLoggedIn(){
    return this.authService.isLoggedIn()
  }

  isWorker(){
    const role = localStorage.getItem('ROLE');
    if(role == 'WORKER' && this.router.url !== "/") return true;
    else return false;
  }

  notOnCPSelectPg(){
    const role = localStorage.getItem('ROLE');
    if(role == 'ADMIN' && this.router.url != "/select_company" && this.router.url !== "/") return true;
    else return false;
  }
}
