import { Component, OnInit, ViewChild } from '@angular/core';
import { ValidationService } from '../providers/validation-service';
import { AuthService } from '../providers/auth.service';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ShowHideInputDirective } from '../directive/showHideInput/show-hide-input.directive';
import { Router } from '@angular/router';
import { ToasterService } from 'angular2-toaster';
import { STATIC_STRING } from './static-string-constants';
import { GLOBAL_STATIC_STRING } from '../constants/global-static-string-constants';

@Component({
  selector: 'app-sign-up-with-email',
  templateUrl: './sign-up-with-email.component.html',
  styleUrls: ['./sign-up-with-email.component.css']
})
export class SignUpWithEmailComponent implements OnInit {

  form: FormGroup;
  errorMessage: Boolean;
  show: Boolean = false;
  isFormSubmitted: Boolean = false;
  @ViewChild(ShowHideInputDirective) input: ShowHideInputDirective;

  static_string = STATIC_STRING;
  global_static_string = GLOBAL_STATIC_STRING;

  constructor(private _fb: FormBuilder,
    public router: Router,
    public authService: AuthService,
    private _toasterService: ToasterService) {
      this.errorMessage = false;
  }

  ngOnInit() {
    this.form = this._fb.group({
      'first_name': ['', Validators.required],
      'last_name': [],
      'passwordCheckbox': [false],
      'email': ['', Validators.required, ValidationService.emailValidator],
      'password': ['', Validators.required, ValidationService.passwordValidator],
      'country': ['', Validators.required]
    });
  }

  get checkFormValidation(): boolean {
    if (this.form.invalid) {
      return false;
    } else {
      return true;
    }
  }

  submit(data) {
    this.isFormSubmitted = true;
    if (this.checkFormValidation) {
      const res = this.authService.emailSignUp(data).then(data => {
        if (data && data.status) {
          this._toasterService.clear();
          this._toasterService.pop('success', 'Success', "Account successfully created. Please verify your email and login again.");
          this.router.navigate(['loginWithEmail']);
        } else {
          this._toasterService.clear();
          this._toasterService.pop('error', 'Error', data.message);
        }
      }, error => {
        this._toasterService.clear();
        this._toasterService.pop('error', 'Error', error.message);
      });
    }
  }

  exisitingUser() {
    this.router.navigate(['loginWithEmail']);
  }

  toggleShow() {
    this.show = !this.show;
    if (this.show) {
      this.input.changeType("text");
    } else {
      this.input.changeType("password");
    }
  }
}
