package com.springBoot.webflowOpgave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Account;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

//    @Autowired
//    private Validator accountValidator;
	
	@GetMapping
	public String showHomePage(Model model) {
		model.addAttribute("account", new Account());
		return "accountForm";
	}

	@PostMapping
	public String onSubmit(@Valid Account account, BindingResult result, Model model) {

//		accountValidator.validate(account, result);

		if (result.hasErrors())
			return "accountForm";
			
		account.simpleExample();
		return "exampleView";
	}

}