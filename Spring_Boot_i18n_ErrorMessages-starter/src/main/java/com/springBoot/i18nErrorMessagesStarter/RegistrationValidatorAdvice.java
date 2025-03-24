package com.springBoot.i18nErrorMessagesStarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice(assignableTypes = RegistrationController.class)
public class RegistrationValidatorAdvice {
	
	@Autowired
	private Validator registrationValidator;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationValidator);
    }
}
