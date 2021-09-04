package ro.netinstructor.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ro.netinstructor.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(final UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return userService.findByEmail(value).isEmpty();
    }
}
