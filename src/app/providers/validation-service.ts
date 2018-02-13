import { ERROR_MESSAGES } from '../constants/error-message-constants';
import { Expressions } from '../constants/regex-constants';
import { Observable } from 'rxjs/Observable';
import { AbstractControl } from '@angular/forms';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';

export class ValidationService {
    static getValidatorErrorMessage(validatorName: string, validatorValue?: any) {
        return ERROR_MESSAGES.us[validatorName];
    }


    // not working
    static firstName(control) {
        if (control.value == null || control.value === '') {
            return Observable.of(!control || !control.value)
                .map(result => !!result ? null : { 'first_name': true });
        } else if (('' + control.value).match(Expressions.maxCharAllowed)) {
            return Observable.of(('' + control.value).match(Expressions.maxCharAllowed))
                .map(result => !!result ? null : { 'maxCharAllowed': true });
        } else if (('' + control.value).match(Expressions.invalidName)) {
            return Observable.of(('' + control.value).match(Expressions.invalidName))
                .map(result => !!result ? null : { 'invalid_name': true });
        } else {
            return null;
        }
    }

    static passwordValidator(control) {
        if (control.value == null || control.value === '') {
            return Observable.of(!control || !control.value)
                .map(result => !!result ? null : { 'invalidPassword': true });
        }
        return Observable.of(('' + control.value).match(control.value.length >= 8 && Expressions.invalidPasword))
            .map(result => !!result ? null : { 'invalidPassword': true });
    };

    static emailValidator(control) {

        if (!control || !control.value) {
            return Observable.of(!control || !control.value)
                .map(result => !!result ? null : { 'invalidEmail': true });
        }
        return Observable.of(('' + control.value).match(Expressions.invalidEmail))
            .map(result => !!result ? null : { 'invalidEmail': true });
    }
    static matchPassword(AC: AbstractControl) {
        const password = AC.get('password').value; // to get value in input tag
        const confirmPassword = AC.get('new_password').value; // to get value in input tag
        if ( password !== confirmPassword) {
            AC.get('new_password').setErrors({ MatchPassword: true })
        } else {
            return null;
        }
    }
    static checkPasswordStrength(control) {
        return Observable.of((control.value).match(control.value.length >= 8 && Expressions.invalidPasword))
            .map(result => !!result ? null : { 'passwordCriteria': true });
    }
}
