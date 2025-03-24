package com.springBoot.oefValidationOplossing;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Numbers;

@Controller
@RequestMapping("/numbers")
public class NumberController {

    @GetMapping
    public String showRegistration(Model model) {
        model.addAttribute("numbers", new Numbers());
        return "numberForm";
    }

    @PostMapping
    public String processRegistration( @Valid Numbers numbers,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "numberForm";
        }
        return "numberSuccess";
    }
}
