import { Component, OnInit } from '@angular/core';
import { AuthService } from '../providers/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-with-social-link',
  templateUrl: './login-with-social-link.component.html',
  styleUrls: ['./login-with-social-link.component.css']
})
export class LoginWithSocialLinkComponent {

  constructor(public router: Router, public authService: AuthService) { }

  loginWithGoogle() {
    this.authService.loginWithGoogle();
  }

  loginWithFacebook() {
    this.authService.loginWithFacebook();
  }

  loginWithEmail() {
    this.router.navigate(['loginWithEmail']);
  }

  signUp() {
    this.router.navigate(['']);
  }
}
