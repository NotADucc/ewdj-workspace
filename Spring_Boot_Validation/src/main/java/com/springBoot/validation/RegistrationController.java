package com.springBoot.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Registration;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private Validator registrationValidator;
    
    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("registration", new Registration());
        return "registrationForm";
    }

    @PostMapping
    public String processRegistration(@Valid Registration registration, 
    		 BindingResult result, Model model) {
    	
    	registrationValidator.validate(registration, result);

        if (result.hasErrors()) {
            return "registrationForm";
        }
        
        registration.setConfirmPassword(null);
        registration.setPassword(null);
        return "registrationSuccess";
    }

}
