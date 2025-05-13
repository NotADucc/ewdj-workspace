package com.example.EWDJ_End.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.user.UserManager;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping(BASE_PATHS.USERS_URI)
public class UserController {
	private final UserManager userManager;
	
	@GetMapping("/favorite/events")
	public String getFavorites(@AuthenticationPrincipal UserDetails user, Model model) {
		model.addAttribute("eventList", userManager.getFavoriteEvents(user.getUsername()));

		return "event-favorites";
	}
}
