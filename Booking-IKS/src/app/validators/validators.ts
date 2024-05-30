import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function noHtmlOrSqlValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    const htmlPattern = /<\/?[^>]+(>|$)/;
    const sqlPattern = /('|\b(SELECT|UPDATE|DELETE|INSERT|DROP|TRUNCATE|ALTER|EXEC)\b)/i;

    if (htmlPattern.test(value) || sqlPattern.test(value)) {
      return { 'invalidInput': true };
    }
    return null;
  };
}

export function lettersOnlyValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    const lettersOnlyPattern = /^[a-zA-Z\s]*$/;

    if (!lettersOnlyPattern.test(value)) {
      return { 'lettersOnly': true };
    }
    return null;
  };
}

export function numbersOnlyValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    const numbersOnlyPattern = /^[0-9]*$/;

    if (!numbersOnlyPattern.test(value)) {
      return { 'numbersOnly': true };
    }
    return null;
  };
}

export function lettersAndSpacesValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    const lettersAndSpacesPattern = /^[a-zA-Z\s]*$/;

    if (!lettersAndSpacesPattern.test(value)) {
      return { 'lettersAndSpacesOnly': true };
    }
    return null;
  };
}
