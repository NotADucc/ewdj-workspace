package com.example.EWDJ_End.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.event.Event;
import domain.event.EventManager;
import domain.room.RoomManager;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/events")
public class EventController {
	@Autowired
	EventManager eventManager;
	@Autowired
	RoomManager roomManager;

	@GetMapping
	public String getEvents(Model model) {
		model.addAttribute("eventList", eventManager.giveEventsSorted());
		return "event-overview";
	}

	@GetMapping("/{id}")
	public String getEvent(
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
	public String postToggleFavorite(
			@PathVariable Integer id,
			@AuthenticationPrincipal UserDetails user,
			Model model
	) {
		eventManager.toggleFavorite(id, user.getUsername());
		model.addAttribute("event", eventManager.giveEvent(id));

		return "redirect:/events/%s".formatted(id);
	}

	@GetMapping("/{id}/edit")
	public String getEdit(
			@PathVariable Integer id,
			@AuthenticationPrincipal UserDetails user,
			Model model
	) {
		model.addAttribute("event", eventManager.giveEvent(id));
		model.addAttribute("roomList", roomManager.giveRooms());
		return "event-cu";
	}

	@PostMapping("/{id}/edit")
	public String postEdit(
			@PathVariable Integer id,
			@AuthenticationPrincipal UserDetails user,
			@Valid Event event,
			BindingResult result,
			Model model
	) {
		if (result.hasErrors()) {
			model.addAttribute("roomList", roomManager.giveRooms());
			return "event-cu";
		}
		
		eventManager.editEvent(id, event);
		return "redirect:/events/%s".formatted(id);
	}
}