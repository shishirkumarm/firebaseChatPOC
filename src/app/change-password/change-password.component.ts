import { Component, OnInit } from '@angular/core';
import { STATIC_STRING } from './static-string-constants';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ValidationService } from '../providers/validation-service';
import { AuthService } from '../providers/auth.service';
import { ToasterService } from 'angular2-toaster';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  static_string = STATIC_STRING;
  form: FormGroup;
  isFormSubmitted: boolean = false;
  passwordExpiry = {
    'show': false,
    'message': ''
  }
  actionCode: String = '';
  mode: String = '';
  disabelSubmit = false;
  displayMode: String = '';
  constructor(private _fb: FormBuilder,
    public router: Router,
    private _auth: AuthService,
    private _toasterService: ToasterService) {
  }

  ngOnInit() {

    this.form = this._fb.group({
        'password': ['', Validators.required, ValidationService.passwordValidator],
        'new_password': ['', Validators.required]
      }, {
        validator: ValidationService.matchPassword
      });
    this.actionCode = this.getParameterByName('oobCode');
    this.mode = this.getParameterByName('mode');

    if (this.mode === 'resetPassword') {
       this.displayMode = 'changePassword';
      this.validatePasswordToken();
    } else if (this.mode === 'verifyEmail') {
      this.displayMode = 'emailVerfication';
      this.validateUserEmailId();
    }
  }

  validateUserEmailId() {
    this._auth.verifyUserEmailId(this.actionCode)
    .then((response) => {
        if (response === 'success') {
          this._toasterService.clear();
          this._toasterService.pop('success', 'Success', 'Email has been verified successfully.');
          this.router.navigate(['loginWithEmail']);
        } else {
          this._toasterService.clear();
          this._toasterService.pop('error', 'Error', response.message);
        }
    })
  }

  validatePasswordToken() {
      this._auth.verifyPasswordCode(this.actionCode)
        .then((email) => {
          if (email.message) {
            this._toasterService.clear();
            this._toasterService.pop('error', 'Error', email.message);
            this.disabelSubmit = true;
          }
        });
  }

  getParameterByName(name) {
    const url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
      results = regex.exec(url);
    if (!results) {
      return null;
    };
    if (!results[2]) {
      return '';
    }
    return decodeURIComponent(results[2].replace(/\+/g, " "));
  }

  get checkFormValidation(): boolean {
    if (this.form.invalid) {
      return false;
    } else {
      return true;
    }
  }

  submit() {
    const data = this.form.value;
    this.isFormSubmitted = true;
    if (this.checkFormValidation) {
      this._auth.updateUserPassword(this.actionCode, data.new_password).then((response) =>{
        if (response === 'success') {
          this._toasterService.clear();
          this._toasterService.pop('success', 'Success', 'Password has been changed successfully.');
          this.router.navigate(['loginWithEmail']);
        } else {
          this._toasterService.clear();
          this._toasterService.pop('error', 'Error', response.message);
        }
      });
    }
  }

}

