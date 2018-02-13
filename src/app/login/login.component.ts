import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { STATIC_STRING } from './static-string-constants';
import { AuthService } from '../providers/auth.service';
import { ToasterService } from 'angular2-toaster';

@Component({
  selector: 'login-bundle',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  static_string = STATIC_STRING;

  constructor(public authService: AuthService,
              public router: Router,
              private _toasterService: ToasterService) {
  }

  loginWithGoogle() {
    this.authService.loginWithGoogle().then(errorResponse => {
      if(errorResponse) {
        this._toasterService.clear();
        this._toasterService.pop('error', 'Error', errorResponse.message);
      }
    });
  }

  loginWithFacebook() {
    this.authService.loginWithFacebook().then(errorResponse => {
      if (errorResponse) {
        this._toasterService.clear();
        this._toasterService.pop('error', 'Error', errorResponse.message);
      }
    });
  }

  signUpWithEmail() {
    this.router.navigate(['signUpWithEmail']);
  }
}
