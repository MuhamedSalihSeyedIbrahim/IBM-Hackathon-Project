import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../Model/User';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  // authenticationApiUrl = environment.baseUrl;
  // loggedInUser = { loggedOut: true };
  // validCredentials: boolean = true;
  // loggedIn: boolean = false;
  // role:string="superadmin";
  //  userRole: string;
  // userid: string;
  // private token: string;
   //isSupplier: boolean = false;
    type: string;
  // isUser: boolean = false;

  userApiUrl = 'http://localhost:8001/user/signup';
  loginApiUrl = 'http://localhost:8001/user/authenticate';
  suppliersApiUrl = 'http://localhost:8001/user/suppliers';

  constructor(private _httpClient: HttpClient, private router: Router, private route: ActivatedRoute) {

  }

  // authenticate(username: string, password: string): Observable<any> {
  //   let credentials = btoa(username + ':' + password);
  //   let headers = new HttpHeaders();
  //   headers = headers.set('Authorization', 'Basic ' + credentials);
  //   return this.httpClient.get(this.authenticationApiUrl + 'authenticate', { headers });
  // }

  // public setToken(token: string) {
  //   this.token = token;
  // }
  // public getToken() {
  //   return this.token;
  // }


  // authenticateUser(user) {
  //   console.log("login user",user);
  //   console.log("auth")
  //   this.authenticate(user.userId, user.password).subscribe(
  //     (data) => {
  //       this.loggedInUser = user;
  //       this.validCredentials = true;
        //  this.userRole = 'supplier';
  //       this.userid = data.username;
  //       console.log(data.username)
//         if (this.userRole == 'admin') {
//         alert('Admin login successful')
//         this.router.navigate(['admin'])
//  }
//         if (this.userRole == 'supplier') {
//            alert('Supplier login successful')
//            this.router.navigate(['supplier'])
//         }
//          if (this.userRole == 'user') {
  //         this.isAdmin = true;
  //         this.isUser = false;
        //    alert('User login successful')
        //    this.router.navigate(['user'])
        //  }
        // }
  //       if (this.userRole == 'user') {
  //         this.isAdmin = false;
  //         this.isUser = true;
  //         alert('User login successful')
  //         this.router.navigate(['search-bar'])
  //       }
  //       this.loggedIn = true;
  //       this.setToken(data.token);
  //       console.log("login success")
  //     },
  //     (error) => {
  //       this.validCredentials = false;
  //     })
  // }

  // logout() {
  //   this.loggedInUser = { loggedOut: true };
  //   this.isAdmin = false;
  //   this.loggedIn = false;
  //   this.isUser = false;
  //   this.setToken(null);
  //   alert('Logged out successfully')
  //   this.router.navigate(['login']);
  // }
  // addUser(user: User) {
  //    if (this.type == "user") {
  //    console.log(user);
    //   return this.httpClient.post<User>(this.authenticationApiUrl + 'users/user', user);
    //  }
    // else {
    //   console.log("user",user);
    //   return this.httpClient.post<User>(this.authenticationApiUrl + 'users/admin', user);
  //    }
  // }
  // signup(userId:String,location:string){
  //   alert("signup successful");
  //   console.log("user Id",userId,"Coord ",location);
  // }
  // adminDetails(): Observable<User[]> {
  //   let headers = new HttpHeaders();
  //   headers = headers.set('Authorization', 'Bearer ' + this.getToken());
  //   return this.httpClient.get<User[]>(this.authenticationApiUrl + 'users/admin', { headers });
  // }

  // response(user: User): Observable<User> {
  //   let headers = new HttpHeaders();
  //   headers = headers.set('Authorization', 'Bearer ' + this.getToken());
  //   return this.httpClient.put<User>(this.authenticationApiUrl + 'users', user, { headers });
  // }

  signup(user: User): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };
    return this._httpClient.post(this.userApiUrl, user, httpOptions);
  }


  authenticateUser(username: string, password: string): Observable<any> {
    const base64Enc = btoa(`${username}:${password}`);
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + base64Enc
      })
    };
    console.log(username+" "+password);
    console.log(base64Enc);
    return this._httpClient.get(this.loginApiUrl, httpOptions);
  }

  getSuppliers(pincode: String): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };
    return this._httpClient.get(`${this.suppliersApiUrl}/${pincode}`, httpOptions);
  }
}
