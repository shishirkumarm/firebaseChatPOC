import { Component, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ValidationService } from '../../../providers/validation-service';

@Component({
  selector: 'error-message',
  template: `<small class="pull-left error" *ngIf="errorMessage !== null">{{errorMessage}}</small>`
})
export class ErrorMessageComponent {
  @Input() control: FormControl;
  @Input() isFormSubmitted: boolean;

  constructor() { }

  get errorMessage() {
    let result: string = null;
    for (const propertyName in this.control.errors) {
      if ((this.control.errors.hasOwnProperty(propertyName) && this.control.touched) || this.isFormSubmitted) {
        result = ValidationService.getValidatorErrorMessage(propertyName, this.control.errors[propertyName]);
      }
    }
    return result;
  }
}
