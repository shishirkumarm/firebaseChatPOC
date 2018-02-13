import { Component, OnInit } from '@angular/core';
import { ValidationService } from '../providers/validation-service';
import { AuthService } from '../providers/auth.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ToasterService } from 'angular2-toaster';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  form: FormGroup;
  isFormSubmitted: boolean = false;
  constructor(private _fb: FormBuilder,
    public authService: AuthService,
    public router: Router,
    private _toasterService: ToasterService) { }

  ngOnInit() {
    this.form = this._fb.group({
      'email': ['', Validators.required]
    });
  }

  get checkFormValidation(): boolean {
    if (this.form.invalid) {
      return false;
    } else {
      return true;
    }
  }

  exisitingUser() {
    this.router.navigate(['loginWithEmail']);
  }

  submit(data) {
    this.isFormSubmitted = true;
    if (this.checkFormValidation) {
      this.authService.getUserDetails(data.email).subscribe((userInfo) => {
        this.authService.resetPassword(data.email).then(resp => {
          if (resp === 'success') {
            this._toasterService.clear();
            this._toasterService.pop('success', 'Success', 'Password reset link mailed to the above Email Id.');
            if (userInfo.length > 0 && !!userInfo[0].blocked) {
              this.authService.blockUser(userInfo[0].$key, false);
            }
          } else {
            this._toasterService.clear();
            this._toasterService.pop('error', 'Error', resp.message);
          }
        }, error => {
          this._toasterService.clear();
          this._toasterService.pop('error', 'Error', error.message);
        });
      });
    }
  }
}
