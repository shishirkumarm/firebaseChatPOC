import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginWithSocialLinkComponent } from './login-with-social-link.component';

describe('LoginWithSocialLinkComponent', () => {
  let component: LoginWithSocialLinkComponent;
  let fixture: ComponentFixture<LoginWithSocialLinkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginWithSocialLinkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginWithSocialLinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
