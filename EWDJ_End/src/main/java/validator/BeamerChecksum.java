package validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Documented
@Constraint(validatedBy = BeamerCheckValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface BeamerChecksum {

    String message() default "{beamerChecksum.message}";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};

	int divisor();
}