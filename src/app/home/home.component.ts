import { NgModule } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../providers/auth.service';
import { HomeService } from './home.service';
import { ToasterService } from 'angular2-toaster';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit{
  isFormSubmitted: Boolean = false;
  private isLoggedIn: Boolean;
  private userList;
  constructor(public authService: AuthService, public router: Router, public homeService: HomeService,
    private _toasterService: ToasterService){
    this.authService.afAuth.authState.subscribe(
      (auth) => {
        if (auth == null) {
          this.isLoggedIn = false;
          this.router.navigate(['']);
        }
      });
  }

  ngOnInit() {

  }

  logout(){
    this.authService.signOut();
    this.router.navigate(['']);
  }

  deleteChatHistory(buddyItem) {

  }
  startChatting(buddyItem) {
    this.homeService.startChatting(buddyItem);
  }
}