package com.springBoot.productStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

//TODO
@ControllerAdvice(assignableTypes = IncreaseDecreaseController.class)
public class PercentValidatorAdvice {
	
	//TODO
	@Autowired
    private Validator percentValidator;
	
	//TODO
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(percentValidator);
    }
}
