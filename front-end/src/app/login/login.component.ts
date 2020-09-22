import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from '../Services/auth-service.service';
import { UserAuthService } from '../Services/user-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup
  isLoginValid = true;


  constructor(private formBuild: FormBuilder, private router: Router, public authService: AuthServiceService,
    public _userAuthService: UserAuthService) { }

  ngOnInit() {
    console.log("login");
    this.loginForm = this.formBuild.group({
      userId: ['', [
        Validators.required
      ]],
      password: ['', [
        Validators.required
      ]]
    })
  }
  get userId() {
    return this.loginForm.get('userId');
  }
  get password() {
    return this.loginForm.get('password');
  }
  toSignupUser() {
    this.router.navigate(['/signup']);
  }

  login() {
    const username = this.loginForm.value.userId;
    const password = this.loginForm.value.password;
    this.authService.authenticateUser(username, password).subscribe((data) => {
      this._userAuthService.setToken(data.token);
      this._userAuthService.setLoggedIn(true);
      this._userAuthService.setRole(data.role);
      this._userAuthService.setUser(data.user);
      if (this._userAuthService.getRole() == 'admin') {
        alert('Admin login successful');
        this.router.navigate(['search']);
      }
      if (this._userAuthService.getRole() == 'supplier') {
        alert('Supplier login successful')
        this.router.navigate(['search'])
      }
      if (this._userAuthService.getRole() == 'customer') {
        alert('User login successful')
        this.router.navigate(['search'])
      }
      if (this._userAuthService.getRole() == 'delivery') {
        alert('User login successful')
        this.router.navigate(['deliveryHome'])
      }
    },
      error => {
        if (error.status = 401) {
          this.isLoginValid = false;
        }
        console.log(JSON.stringify(error));
      });
  }
}

