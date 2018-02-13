import { Component, OnInit, NgZone, ChangeDetectorRef } from '@angular/core';
import { ValidationService } from '../providers/validation-service';
import { AuthService } from '../providers/auth.service';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'location-update',
  templateUrl: './locationUpdate.component.html',
  styleUrls: ['./locationUpdate.component.scss']
})
export class LocationUpdateComponent implements OnInit {
  form: FormGroup;
  locationList: Array<any>;
  isFormSubmitted: Boolean = false;
  email: string;

  constructor(private _fb: FormBuilder,
    public router: Router,
    public authService: AuthService, private _changeDetectorRef: ChangeDetectorRef) {

    this.form = this._fb.group({
      'email': ['', Validators.required],
      'country': ['', Validators.required]
    });

    this.authService.afAuth.auth.onAuthStateChanged(userDetails => {
      if (userDetails) {
        this.email = userDetails.email;
        this.form.controls['email'].setValue(this.email);
      }
    });
  }

  ngOnInit() {
  }

  checkFormValidation(): boolean {
    if (this.form.invalid) {
      return false;
    } else {
      return true;
    }
  }

  newUserSignup() {
    this.router.navigate(['']);
  }

  submit() {
    this.isFormSubmitted = true;
    this._changeDetectorRef.detectChanges();
    if (this.checkFormValidation()) {
      this.authService.updateUserLocation(this.form.value);
    }
  }
}
