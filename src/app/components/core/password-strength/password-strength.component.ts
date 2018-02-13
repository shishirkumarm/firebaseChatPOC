import { Component, OnInit, Input, OnChanges, SimpleChange } from '@angular/core';

@Component({
  selector: 'app-password-strength',
  templateUrl: './password-strength.component.html',
  styleUrls: ['./password-strength.component.css']
})
export class PasswordStrengthComponent implements OnInit, OnChanges {

  @Input() password;
  strength;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: { [propName: string]: SimpleChange }) {

    const currentValue = changes['password'].currentValue;

    if (currentValue) {

      const isSatisfied = (criteria) => {
        return criteria ? 1 : 0;
      };

      this.strength = isSatisfied(changes['password'] && currentValue.length >= 8) +
        isSatisfied(changes['password'] && /[A-z]/.test(currentValue)) +
        isSatisfied(changes['password'] && /(?=.*\W)/.test(currentValue)) +
        isSatisfied(changes['password'] && /\d/.test(currentValue));

    } else {
      this.strength = 0;
    }


  }

}
