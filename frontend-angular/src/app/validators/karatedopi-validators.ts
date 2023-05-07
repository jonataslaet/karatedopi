import { FormControl, ValidationErrors } from '@angular/forms';
export class KaratedopiValidators {
    static notOnlyWhitespace(control: FormControl): ValidationErrors {
        if ((control != null) && (control.value != null) && control.value.length === 0) {
            return {'notOnlyWhitespace': true}
        }
        return null;
    }
}