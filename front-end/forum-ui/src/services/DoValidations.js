import Validations from "../services/Validations";

export default class DoValidations {
    constructor(email, password) {
        this.email = email;
        this.password = password;
    }

    checkValidations() {
        let errors = [];

        //check email
        if(!Validations.checkEmail(this.email)) {
            errors['email'] = 'Invalid email';
        }
        //check password
        if(!Validations.passwordLengthError(this.password, 6)) {
            errors['password'] = 'Password must be at least 6 characters';
        }
        return errors;
    }

}