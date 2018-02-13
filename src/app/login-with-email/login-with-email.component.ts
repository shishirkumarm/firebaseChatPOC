import { Component, OnInit } from '@angular/core';
import { ValidationService } from '../providers/validation-service';
import { AuthService } from '../providers/auth.service';
import { ToasterService } from 'angular2-toaster';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import { STATIC_STRING } from './static-string-constants';

@Component({
  selector: 'app-login-with-email',
  templateUrl: './login-with-email.component.html',
  styleUrls: ['./login-with-email.component.css']
})

export class LoginWithEmailComponent implements OnInit {
  form: FormGroup;
  erroMessage: Boolean;
  static_string = STATIC_STRING;
  message: String = '';
  isFormSubmitted: Boolean = false;
  resendVerificationEmail = false;
  
  constructor(private _fb: FormBuilder,
    public router: Router,
    public authService: AuthService,
    private _toasterService: ToasterService) {
    this.erroMessage = false;
  }
  ngOnInit() {
    this.form = this._fb.group({
      'email': [localStorage.usrname, Validators.required],
      'password': [localStorage.pass, Validators.required],
      'rememberMe': [false]
    });
  }

  get checkFormValidation(): boolean {
    if (this.form.invalid) {
      return false;
    } else {
      return true;
    }
  }

  rememberMe() {
    if (this.form.controls.email.value && this.form.controls.rememberMe.value) {
      // save id and password
      localStorage.usrname = this.form.controls.email.value;
      localStorage.pass = this.form.controls.password.value;
      localStorage.chkbx = this.form.controls.rememberMe.value;
    } else {
      localStorage.usrname = '';
      localStorage.pass = '';
      localStorage.chkbx = '';
    }
  }

  submit(user) {
    this.isFormSubmitted = true;
    this.authService.getUserDetails(user.email).subscribe((userInfo) => {
      if (this.checkFormValidation) {
        this.rememberMe();
        this.authService.emailLogin(user.email, user.password)
          .then(data => {
            if (userInfo.length > 0 && userInfo[0].blocked === true) {
              this.handleBlockedUser(true, data.message, userInfo[0].$key);
            } else {
              if (data && data.status) {
                if (data.emailVerified) {
                  this.router.navigate(['home']);
                } else {
                  this._toasterService.clear();
                  this._toasterService.pop('error', 'Error', "Please verify your email and login again.");
                  this.resendVerificationEmail = true;
                }
              } else {
                if (data.message === 'We have blocked all requests from this device due to unusual activity. Try again later.') {
                  this.handleBlockedUser(true, data.message, userInfo[0].$key);
                } else {
                  this._toasterService.clear();
                  this._toasterService.pop('error', 'Error', data.message);
                }
              }
            }
          }, error => {
            this._toasterService.clear();
            this._toasterService.pop('error', 'Error', error.message);
          });
      }
    });
  }

  handleBlockedUser(flag: Boolean, message: any, userInfo: String) {
    if (flag) {
        this.authService.blockUser(userInfo, true);
        this._toasterService.clear();
        this._toasterService.pop('error', 'Error', "We have blocked all requests due to unusual activity. Please reset your current password.");
        this.forgotPassword();
    }
  }

  forgotPassword() {
    this.router.navigate(['resetPassword']);
  }

  loginWithSocialLink() {
    this.router.navigate(['loginWithSocialLink']);
  }

  newUserSignup() {
    this.router.navigate(['']);
  }
}
