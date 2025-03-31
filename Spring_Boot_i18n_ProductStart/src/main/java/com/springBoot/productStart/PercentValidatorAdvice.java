package com.springBoot.productStart;

import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

//TODO
public class PercentValidatorAdvice {
	
	//TODO
    private Validator percentValidator;
	
	//TODO
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(percentValidator);
    }
}
