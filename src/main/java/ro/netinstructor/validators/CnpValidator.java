package ro.netinstructor.validators;

import ro.netinstructor.utility.Utilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CnpValidator implements ConstraintValidator<CnpValid, String> {

    @Override
    public void initialize(final CnpValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return Utilities.verificareCnp(value);
    }
}
