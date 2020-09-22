import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../Model/User';
import { AuthServiceService } from '../Services/auth-service.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  choice = ["Male", "Female"];
  signUpForm: FormGroup;

  constructor(private router: Router, private authService: AuthServiceService, private route: ActivatedRoute, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.authService.type = this.route.snapshot.paramMap.get('type');
    this.signUpForm = this.formBuilder.group({
      userId: ['', [
        Validators.required,
        this.isUsernameTaken
      ]],
      firstname: ['', [
        Validators.required
      ]],
      lastname: ['', [
        Validators.required
      ]],
      age: ['', [
        Validators.required
      ]],
      contact: ['', [
        Validators.required
      ]],
      password: ['', [
        Validators.required
      ]],
      confirmPassword: ['', [
        Validators.required,
        this.matchConfirmPassword.bind(this)
      ]]
    })
  }
  get userId() {
    return this.signUpForm.get('userId');
  }
  get firstname() {
    return this.signUpForm.get('firstname');
  }
  get lastname() {
    return this.signUpForm.get('lastname');
  }
  get age() {
    return this.signUpForm.get('age');
  }
  get contact() {
    return this.signUpForm.get('contact');
  }
  get password() {
    return this.signUpForm.get('password');
  }
  get confirmPassword() {
    return this.signUpForm.get('confirmPassword');
  }
  matchConfirmPassword(formControl: FormControl): { [s: string]: boolean } {
    if (this.signUpForm) {
      if (formControl.value && formControl.value.length > 0 && formControl.value !== this.signUpForm.get('password').value) {
        return { 'nomatch': true };
      }
    }
    return null;
  }
  isUsernameTaken(formControl: FormControl): { [s: string]: boolean } {
    if (formControl.value === 'admin') {
      return { 'userNameTaken': true };
    } else {
      return null;
    }
  }

  addUser(user: User) {
    console.log(user);
    // users.latitude= coord.wf.lat;
    // this.authService.addUser(user);
    // .subscribe((data) => {
    alert('Proceed to Add Location');
    this.router.navigate(['map-search', user.userId]);
    // })
    // this.authService.addUser(users).subscribe(
    //   data => {
    //     if (data) {
    //       console.log("logged");
    //       console.log('Signup success')
    //       this.router.navigate(['login'])
    //     }
    //     else {
    //       alert('Already exist');
    //       this.router.navigate(['signup'])
    //     }
    //   });
  }
}


