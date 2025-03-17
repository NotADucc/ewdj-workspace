package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Account;

public class AccountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Account.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
        Account account = (Account) target;
    	var percent = account.getPercent();
            
//        if (((int) (percent * 100) & 1) == 1) {
//            errors.rejectValue("percent",
//            		"",
//                    "percent is not even.");
//        }
		
    	if (percent == null)
    		return;
    	
		if (((int) (percent * 100) & 1) == 1) {
			errors.rejectValue("percent", "", "percent is not even.");
		}
	}
	
	//if ((int) (percent * 100) % 2 != 0) {
}
