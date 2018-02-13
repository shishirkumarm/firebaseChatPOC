import { Injectable } from '@angular/core';
import { AngularFireDatabase, FirebaseListObservable, FirebaseObjectObservable } from 'angularfire2/database';
import { AngularFireAuth } from 'angularfire2/auth';
import { ToasterService } from 'angular2-toaster';
import * as firebase from 'firebase';
import { FirebaseApp } from 'angularfire2';
import { AuthService } from '../providers/auth.service';
import { Router } from '@angular/router';

@Injectable()
export class ChatroomService {

  authState: any = null;
  usersList: FirebaseListObservable<any>;
  chatList: FirebaseListObservable<any>;
  buddyId = '';
  activeChatRooms = '';
  buddyObject: FirebaseListObservable<any>;

  constructor(public afAuth: AngularFireAuth, private db: AngularFireDatabase, private authService: AuthService,
    private _toasterService: ToasterService, private router: Router) {

    this.afAuth.authState.subscribe((auth) => {
      this.authState = auth;
    });

    this.buddyId = localStorage.getItem('buddyId');
    this.activeChatRooms = localStorage.getItem('activeChatRooms');
    if (this.activeChatRooms) {
        this.fetchChatHistory();
    }
  }

  private createChatId() {
    let chatId = '';
    const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 9; i++) {
      chatId += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return chatId;
  }

  createNewRoom(buddyInfo, message): void {
    this.activeChatRooms = this.createChatId();
    const path = `chatrooms/${this.activeChatRooms}`;
    const data = {
      messages: [{
        author: this.authService.currentUserId,
        body: message
      }],
      members: [this.authService.currentUserId, buddyInfo]
    }
    this.db.object(path).update(data).catch(error => {
      console.log(error);
    });

    this.updateUserActiveId(buddyInfo);
    this.updateUserActiveId(this.authService.currentUserId);
    this.fetchChatHistory();
  }

  updateUserActiveId(userId) {
    const data = {
        id: this.activeChatRooms
    }
    const path = `userRegistry/${userId}/activeChatRooms`;
    this.db.list(path).push(data).catch((errorObj) => {
        console.log(errorObj);
    });
  }

  postChatMessage(message) {
    const path = `chatrooms/${this.activeChatRooms}/messages`;
    const data = {
        author: this.authService.currentUserId,
        body: message
      }
      this.db.list(path).push(data).catch((errorObj) => {
        console.log(errorObj);
      });
  }

  updateUserListObject() {
    this.fetchChatHistory();
  }

  fetchChatHistory(){
    const chatListPath = `chatrooms/${this.activeChatRooms}/messages`;
    this.chatList = this.db.list(chatListPath);
  }
}