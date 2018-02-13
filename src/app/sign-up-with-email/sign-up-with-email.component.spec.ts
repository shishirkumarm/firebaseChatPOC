import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpWithEmailComponent } from './sign-up-with-email.component';

describe('SignUpWithEmailComponent', () => {
  let component: SignUpWithEmailComponent;
  let fixture: ComponentFixture<SignUpWithEmailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignUpWithEmailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpWithEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
