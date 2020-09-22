import { Component } from '@angular/core';
import hello from '../assets/js/hello';
import { UserAuthService } from 'src/app/Services/user-auth.service';
import { HomeComponent } from './home/home.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'request-management';

  constructor(private userAuthService : UserAuthService ) { }


  ngOnInit() {
  }

  isLoggedIn() {
    return this.userAuthService.loggedIn();
  }

  isAdmin() {
    return this.userAuthService.getRole() === 'admin';
  }
 
  isCustomer(){
    return this.userAuthService.getRole() === "customer";
  }

  isSupplier(){
    return this.userAuthService.getRole() === "supplier";
  }

  isDelivery(){
    return this.userAuthService.getRole() === "delivery";
  }

  home(){
    this.userAuthService.setHome(true);
  }

  isHome(){
    return this.userAuthService.getHome();
  }
  

  logout() {
    this.userAuthService.setRole(null);
    this.userAuthService.setUser(null);
    this.userAuthService.setLoggedIn(false);
    this.userAuthService.setToken(null);
  }
}
