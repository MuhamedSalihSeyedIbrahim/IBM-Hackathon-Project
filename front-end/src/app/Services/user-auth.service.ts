import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {

   isLoggedIn = false;
   role: string;
   token: string;
   user: string;
   userId: number;
   home = false;
  constructor() { }

  getToken() {
    return this.token;
  }

  setToken(token: string) {
    this.token = token;
  }

  getHome() {
    return this.home;
  }

  setHome(home: boolean) {
    this.home = home;
  }

  getRole() {
    return this.role;
  }
  setRole(role: string) {
    this.role = role;
  }
  loggedIn() {
    return this.isLoggedIn;
  }
  setLoggedIn(status: boolean) {
    this.isLoggedIn = status;
  }

  getUser() {
    return this.user;
  }
  setUser(user: string) {
    this.user = user;
  }
  getUserId() {
    return this.userId;
  }
  setUserId(userId: number) {
    this.userId = userId;
  }
}
