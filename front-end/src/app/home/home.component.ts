import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../Services/user-auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private userAuthService : UserAuthService) { }
  ngOnInit(): void {
  }

  isHome(){
    return this.userAuthService.getHome();
  }
  
  isLoggedIn() {
    return this.userAuthService.loggedIn();
  }
}
