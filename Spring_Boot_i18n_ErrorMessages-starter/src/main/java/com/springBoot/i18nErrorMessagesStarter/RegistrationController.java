package com.springBoot.i18nErrorMessagesStarter;

import java.util.Locale;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Registration;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @GetMapping
    public String showRegistration(Model model) {
        Registration registration = new Registration();
        model.addAttribute("registration", registration);
        return "registrationForm";
    }

    @PostMapping
    public String processRegistration(@Valid Registration registration,
            BindingResult result, Model model, Locale locale) {
    
        if (result.hasErrors()) {     	
            return "registrationForm";
        }

        registration.setConfirmPassword(null);
        registration.setPassword(null);

        return "registrationSuccess";
    }
}
