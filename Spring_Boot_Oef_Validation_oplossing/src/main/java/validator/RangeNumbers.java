package validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Documented
@Constraint(validatedBy = RangeConstraintValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface RangeNumbers {

    String message() default "number1 and number2 must be within a range of {range}";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};

	int range();
}