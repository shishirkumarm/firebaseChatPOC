import { NgModule } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../providers/auth.service';
import { ChatroomService } from './chatroom-component.service';

import { ToasterService } from 'angular2-toaster';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './chatroom-component.component.html',
  styleUrls: ['./chatroom-component.component.css']
})
export class ChatroomComponentComponent {
  form: FormGroup;
  isFormSubmitted: Boolean = false;
  private isLoggedIn: Boolean;
  private userList;
  constructor(public authService: AuthService, public router: Router, public chatroomService: ChatroomService,
    private _fb: FormBuilder, private _toasterService: ToasterService){
    this.authService.afAuth.authState.subscribe(
      (auth) => {
        if (auth == null) {
          this.isLoggedIn = false;
          this.router.navigate(['']);
        }
      });
  }

  ngOnInit() {
    this.form = this._fb.group({
      'chatMessage': ['']
    });
  }
}