package validator;

import domain.Price;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PercentValidator implements Validator {

    @Override
    public boolean supports(Class<?> klass) {
        return Price.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

}