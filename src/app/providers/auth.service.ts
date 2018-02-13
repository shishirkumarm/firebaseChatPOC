import { Injectable } from '@angular/core';
import { AngularFireDatabase, FirebaseListObservable, FirebaseObjectObservable } from 'angularfire2/database';
import { AngularFireAuth } from 'angularfire2/auth';
import { ToasterService } from 'angular2-toaster';
import { Router } from '@angular/router';
import * as firebase from 'firebase';
import { FirebaseApp } from 'angularfire2';

@Injectable()
export class AuthService {

  authState: any = null;
  locationList: FirebaseListObservable<any>;
  usersList: FirebaseListObservable<any>;

  state = {
    loggedIn: false
  };

  constructor(public afAuth: AngularFireAuth,
    private db: AngularFireDatabase,
    private router: Router,
    private _toasterService: ToasterService) {

    this.afAuth.authState.subscribe((auth) => {
      this.authState = auth;
    });

    this.locationList = db.list('/location_table');
  }

  get authenticated(): boolean {
    return this.authState !== null;
  }

  get currentUser(): any {
    return this.authenticated ? this.authState : null;
  }

  get currentUserObservable(): any {
    return this.afAuth.authState;
  }

  get currentUserId(): string {
    return this.authenticated ? this.authState.uid : '';
  }

  get userPhotoUrl(): string {
    return this.authenticated ? this.authState['photoURL'] : '';
  }

  get userPhoneNumber(): string {
    return this.authenticated ? this.authState['phoneNumber'] : '';
  }

  get userEmail(): string {
    return this.authenticated ? this.authState['email'] : '';
  }

  get userEmailVerified(): string {
    if (!this.authState) {
      return 'false';
    } else {
      return this.authState['emailVerified'] ? 'true' : 'false';
    }
  }

  get currentUserAnonymous(): boolean {
    return this.authenticated ? this.authState.isAnonymous : false;
  }

  get currentUserDisplayName(): string {
    if (!this.authState) {
      return 'Guest';
    } else if (this.currentUserAnonymous) {
      return 'Anonymous';
    } else {
      return this.authState['displayName'] || '';
    }
  }

  get serviceProviderId(): string {
    if (!this.authState) {
      return null;
    } else if(this.authState.providerData[0]) {
      return this.authState.providerData[0].providerId;
    } else {
      return 'Unknown';
    }
  }

  loginWithGoogle() {
    const provider = new firebase.auth.GoogleAuthProvider();
    return this.socialSignIn(provider);
  }

  loginWithFacebook() {
    const provider = new firebase.auth.FacebookAuthProvider();
    return this.socialSignIn(provider);
  }

  validateApplicationId(applicationId: Number): FirebaseListObservable<any> {
    const applicationStatus = this.db.list('/applicationList', {
      query: {
        orderByChild: 'applicationId',
        equalTo: applicationId,
      }
    });
    return applicationStatus;
  }

  emailSignUp(formData) {
    return this.afAuth.auth.createUserWithEmailAndPassword(formData.email, formData.password)
      .then((user) => {
        this.authState = user;
        this.sendEmailVerification();
        this.newUserData(formData);
        user.status = true;
        return user;
      })
      .catch(error => {
        return error;
      });
  }

  sendEmailVerification() {
    const user: any = firebase.auth().currentUser;
    user.sendEmailVerification()
      .then((success) => {
        console.log('Please verify your email');
      })
      .catch(error => {
        return error;
      });
  }

  emailLogin(email: string, password: string) {
    return this.afAuth.auth.signInWithEmailAndPassword(email, password)
      .then((user) => {
        this.authState = user;
        if (user.emailVerified) {
          this.registerUserLog(user);
          this.fetchActiveRoom();
          user.status = true;
          return user;
        } else {
          user.message = 'Please verify your email, then log in.';
          return user;
        }
      })
      .catch(error => {
        return error;
      });
  }

  verifyUserEmailId(code) {
    const auth: any = firebase.auth();
    return auth.applyActionCode(code)
      .then((resp) => {
        return resp = 'success';
      })
      .catch((error) => {
        return error;
      });
  }

  verifyPasswordCode(code) {
    const auth: any = firebase.auth();

    return auth.verifyPasswordResetCode(code)
      .then((email) => {
        return email;
      })
      .catch((error) => {
        return error;
      });
  }

  updateUserPassword(code, newPassword) {
    const auth: any = firebase.auth();

    return auth.confirmPasswordReset(code, newPassword)
      .then((data) => {
        return data = 'success';
      })
      .catch((error) => {
        return error;
      });
  }

  resetPassword(email: string) {
    const auth: any = firebase.auth();

    return auth.sendPasswordResetEmail(email)
      .then((data) => {
        return data = 'success';
      })
      .catch((error) => {
        return error;
      });
  }

  signOut(): void {
    this.afAuth.auth.signOut();
    this.router.navigate(['']);
  }

  updateUserLocation(formData): void {
    const path = `userRegistry/${this.currentUserId}/`;
    const locationData = {
      location: formData.country
    }

    this.db.object(path).update(locationData).then(data => {
      this.router.navigate(['home']);
    })
      .catch(error => {
        console.log(error);
      });
  }

  setUserDetailsToStaorage(){
    const data = {
            'userDisplayName': this.currentUserDisplayName,
            'userEmail': this.userEmail,
            'providerId': this.serviceProviderId,
            'userUid': this.currentUserId,
          };
    return data;
  }

  private socialSignIn(provider) {
    return this.afAuth.auth.signInWithPopup(provider)
      .then((respObject) => {
        this.authState = respObject.user;
        this.getUserDetails(this.authState.email).subscribe((details) => {
          if (details && details.length > 0 && details[0].location) {
            this.registerUserLog(details);
            this.fetchActiveRoom();
            this.router.navigate(['home']);
          } else {
            this.newUserData(null);
            this.router.navigate(['locationupdate']);
          }
        });
      })
      .catch(error => {
        return error;
      });
  }

  getUserDetails(emailId: String): FirebaseListObservable<any> {
    this.usersList = this.db.list('/userRegistry', {
      query: {
        orderByChild: 'email',
        equalTo: emailId,
      }
    });
    return this.usersList;
  }

  fetchActiveRoom() {
    const roomRef = firebase.database().ref('userRegistry');
    roomRef.orderByChild("email").equalTo(this.userEmail).on("child_added", function(data) {
      let activeChatRoomsCollection = [];
      for (const item in data.val().activeChatRooms) {
        const id = data.val().activeChatRooms[item].id;
        activeChatRoomsCollection.push(id);
      }
      localStorage.setItem('usersChatRoomsCollection', JSON.stringify(activeChatRoomsCollection));
    });
  }

  private registerUserLog(userDetails): void {
    const path = `registerUserLog/${this.currentUserId}`;
    const data = {
      loginTime: Date.now()
    }
    this.db.object(path).update(data)
      .catch(error => {
        console.log(error)
      });
  }

  private newUserData(formData): void {
    let name: String = '';
    let email: String = '';
    let location: String = '';
    let photoUrl = '';
    let activeChatRoom = [];
    if (formData) {
      name = formData.first_name + formData.last_name;
      email = formData.email;
      photoUrl = '';
      location = formData.country;
      activeChatRoom = activeChatRoom
    } else {
      name = this.authState.displayName;
      email = this.authState.email;
      photoUrl = this.userPhotoUrl,
      location = '';
      activeChatRoom = activeChatRoom
    }

    const data = {
      email,
      name,
      location,
      photoUrl,
      activeChatRoom
    }

    const path = `userRegistry/${this.currentUserId}`;

    this.db.object(path).update(data)
      .catch(error => {
        console.log(error)
      });
  }

  userLoggedIn() {
    return new Promise(function (resolve, reject) {
      firebase.auth().onAuthStateChanged(function (user) {
        if (user) {
          resolve(user.email);
          console.log(user);
        } else {
          reject(Error('It broke'));
        }
      });
    });
  }

  blockUser(key, value): void {
    const path = `userRegistry/${key}`;
    const data = {
      blocked: value
    };

    this.db.object(path).update(data)
      .catch(error => console.log(error));
  }


}
