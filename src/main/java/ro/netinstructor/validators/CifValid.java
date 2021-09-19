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
public @interface CifValid {

    String message() default "CIF incorect. Verificati CIF-ul si incercati din nou";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
