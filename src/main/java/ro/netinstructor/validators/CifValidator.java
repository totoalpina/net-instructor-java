package ro.netinstructor.validators;

import ro.netinstructor.utility.Utilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CifValidator implements ConstraintValidator<CifValid, String> {

    @Override
    public void initialize(final CifValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Utilities.verificareCif(value);
    }
}
