package com.example.EWDJ_End.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.event.Event;
import domain.event.EventManager;
import domain.room.RoomManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping(BASE_PATHS.EVENTS_URI)
public class EventController {
	private final EventManager eventManager;
	private final RoomManager roomManager;

	@GetMapping
	public String getEvents(Model model) {
		model.addAttribute("eventList", eventManager.getEventsSorted());
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

	@GetMapping("/create")
	public String getCreateEvent(@AuthenticationPrincipal UserDetails user, Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("roomList", roomManager.giveRooms());
		model.addAttribute("cu", "create");
		return "event-cu";
	}

	@PostMapping("/create")
	public String postCreateEvent(
			@AuthenticationPrincipal UserDetails user,
			@Valid Event event,
			BindingResult result,
			Model model
	) {
		if (result.hasErrors()) {
			model.addAttribute("roomList", roomManager.giveRooms());
			model.addAttribute("cu", "create");
			return "event-cu";
		}
		eventManager.addEvent(event);
		return "redirect:/events";
	}

	@GetMapping("/favorites")
	public String getFavorites(@AuthenticationPrincipal UserDetails user, Model model) {
		model.addAttribute("eventList", eventManager.getFavoriteEventsForUser(user.getUsername()));

		return "event-favorites";
	}

	@PostMapping("/{id}/favorite")
	public String postToggleFavorite(
			@PathVariable Integer id,
			@AuthenticationPrincipal UserDetails user,
			Model model
	) {
		eventManager.toggleFavorite(id, user.getUsername());
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
		model.addAttribute("cu", "edit");
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
			model.addAttribute("cu", "edit");
			return "event-cu";
		}

		eventManager.editEvent(id, event);
		return "redirect:/events/%s".formatted(id);
	}
}