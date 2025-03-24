package com.springBoot.oefValidationOplossing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice(assignableTypes = NumberController.class)
public class NumberValidatorAdvice {
	
	@Autowired
    private Validator numberValidator;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(numberValidator);
    }
}
