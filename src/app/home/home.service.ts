import { Injectable } from '@angular/core';
import { AngularFireDatabase, FirebaseListObservable } from 'angularfire2/database';
import { AngularFireAuth } from 'angularfire2/auth';
import * as firebase from 'firebase';
import { Router } from '@angular/router';
import { AuthService } from '../providers/auth.service';

@Injectable()
export class HomeService {

  authState: any = null;
  usersList: FirebaseListObservable<any>;
  constructor(public afAuth: AngularFireAuth, private db: AngularFireDatabase, private authService: AuthService,
    private router: Router) {

    this.afAuth.authState.subscribe((auth) => {
      this.authState = auth;
    });

    this.usersList = this.db.list('/userRegistry');
  }

  startChatting(buddyInfo){
    let usersChatRoomsCollection = localStorage.getItem('usersChatRoomsCollection');
    //Check the below code. Not sure why is it coded.
    //localStorage.clear();
    localStorage.setItem('buddyId', buddyInfo.$key);
    localStorage.setItem('buddyName', buddyInfo.name);
    localStorage.setItem('buddyPhotoUrl', buddyInfo.photoUrl);
    usersChatRoomsCollection = JSON.parse(usersChatRoomsCollection);
    if(buddyInfo.activeChatRooms && usersChatRoomsCollection){
      for (const item in buddyInfo.activeChatRooms) {
        if(usersChatRoomsCollection.indexOf(buddyInfo.activeChatRooms[item].id) > -1) {
          localStorage.setItem('activeChatRooms', buddyInfo.activeChatRooms[item].id);
        }
      }
    }
    this.router.navigate(['chaatroom']);
  }
}