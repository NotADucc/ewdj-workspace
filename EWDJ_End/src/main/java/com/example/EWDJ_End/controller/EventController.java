package com.example.EWDJ_End.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.event.EventManager;

@Controller
@RequestMapping("/events")
public class EventController {
	@Autowired
	EventManager eventManager;

	@GetMapping
	public String showLogin(Model model) {
		model.addAttribute("eventList", eventManager.giveEventsSorted());
		return "event-overview";
	}

	@GetMapping("/{id}")
	public String showEventDetails(
			@PathVariable Integer id,
			@AuthenticationPrincipal UserDetails user,
			Model model
	) {
		model.addAttribute("event", eventManager.giveEvent(id));
		model.addAttribute("isFavorited", eventManager.isFavorited(id, user.getUsername()));
		model.addAttribute(
				"favoriteCountExceeded",
				eventManager.hasMaxFavoriteCountBeenExceeded(user.getUsername())
		);
		return "event-details";
	}

	@PostMapping("/{id}/favorite")
	public String toggleFavorite(
			@PathVariable Integer id,
			@AuthenticationPrincipal UserDetails user,
			Model model
	) {
		eventManager.toggleFavorite(id, user.getUsername());
		model.addAttribute("event", eventManager.giveEvent(id));

		return "redirect:/events/%s".formatted(id);
	}
}