package validator;

import domain.Numbers;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class NumberValidator implements Validator{

    @Override
    public boolean supports(Class<?> klass) {
        return Numbers.class.
                isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, 
            Errors errors) {
        
        Numbers form = (Numbers) target;
             
        if (form.getNumber1() == null || form.getNumber2() == null)
        	return; 
        
        if (form.getNumber1() >= form.getNumber2()) {
            errors.rejectValue("number1",
                    "lengthOfUser.numberForm.number1",
                    "number1 < number2");
        }
    }
}