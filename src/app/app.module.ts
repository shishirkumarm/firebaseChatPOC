import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { ToasterModule, ToasterService } from 'angular2-toaster';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AngularFireModule } from 'angularfire2';
import { firebaseConfig } from './../environments/firebase.config';
import { AngularFireDatabaseModule, AngularFireDatabase } from 'angularfire2/database';
import { AngularFireAuthModule, AngularFireAuth } from 'angularfire2/auth';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { LocationUpdateComponent } from './locationUpdate/locationUpdate.component';

// Features
import { AuthService } from './providers/auth.service';
import { HomeService } from './home/home.service';
import { ChatroomService } from './chatroom-component/chatroom-component.service';
import { HeadersService } from './../environments/http-header-constants';

import { HomeComponent } from './home/home.component';
import { PasswordStrengthComponent } from './components/core/password-strength/password-strength.component';
import { SignUpWithEmailComponent } from './sign-up-with-email/sign-up-with-email.component';
import { ShowHideInputDirective } from './directive/showHideInput/show-hide-input.directive';
import { LoginWithSocialLinkComponent } from './login-with-social-link/login-with-social-link.component';
import { LoginWithEmailComponent } from './login-with-email/login-with-email.component';

import { ErrorMessageComponent } from './components/core/error-message/error-message.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { ChatroomComponentComponent } from './chatroom-component/chatroom-component.component';
import { ProfileComponentComponent } from './profile-component/profile-component.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'profile', component: ProfileComponentComponent },
  { path: 'chaatroom', component: ChatroomComponentComponent },
  { path: 'locationupdate', component: LocationUpdateComponent },
  { path: 'signUpWithEmail', component: SignUpWithEmailComponent },
  { path: 'loginWithSocialLink', component: LoginWithSocialLinkComponent },
  { path: 'loginWithEmail', component: LoginWithEmailComponent },
  { path: 'resetPassword', component: ResetPasswordComponent },
  { path: 'verifyDetails', component: ChangePasswordComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    PasswordStrengthComponent,
    SignUpWithEmailComponent,
    ShowHideInputDirective,
    LocationUpdateComponent,
    LoginComponent,
    LoginWithSocialLinkComponent,
    LoginWithEmailComponent,
    ErrorMessageComponent,
    ResetPasswordComponent,
    ChangePasswordComponent,
    ChatroomComponentComponent,
    ProfileComponentComponent
  ],
  imports: [
    BrowserModule,
    AngularFireModule.initializeApp(firebaseConfig),
    FormsModule,
    HttpModule,
    ToasterModule,
    RouterModule.forRoot(routes),
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [AuthService,
              HomeService,
              ChatroomService,
              ToasterService,
              HeadersService,
              { provide: LocationStrategy,
                useClass: HashLocationStrategy
              }],
  bootstrap: [AppComponent]
})
export class AppModule { }
