package ro.netinstructor.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CifValidator.class)
public @interface CnpValid {

    String message() default "CNP incorect. Verificati CNP-ul si incercati din nou";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
