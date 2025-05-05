package com.springBoot.securityRoles;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcomeNext")
public class WelcomeNextController {

	@GetMapping
	public String listStudents(Model model, Authentication authentication) {
		//model.addAttribute("username", authentication.getName());
		return "welcomeNext";
	}

}