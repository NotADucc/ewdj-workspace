package com.springBoot_JPA_Security_1;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.MyUser;
import repository.UserRepository;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String listGuest(Model model, Principal principal) {

		MyUser user = userRepository.findByUsername(principal.getName());
		model.addAttribute("city", user.getCity());
		model.addAttribute("username", principal.getName());		
		return "hello";
	}
}
