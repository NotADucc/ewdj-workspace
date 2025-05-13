package com.springBoot.EWDJ_End.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.springBoot.EWDJ_End.controller.EventController;

@ControllerAdvice(assignableTypes = EventController.class)
public class ConferenceRangeValidatorAdvice {
	
	@Autowired
    private Validator conferenceRangeValidator;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(conferenceRangeValidator);
    }
}
