package com.example.EWDJ_End.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.room.Room;
import domain.room.RoomManager;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/rooms")
public class RoomController {
	@Autowired
	RoomManager roomManager;

	@GetMapping
	public String getEvents(Model model) {
		model.addAttribute("roomList", roomManager.giveRooms());
		return "room-overview";
	}

	@GetMapping("/create")
	public String getCreateRoom(@AuthenticationPrincipal UserDetails user, Model model) {
		model.addAttribute("room", new Room());
		model.addAttribute("cu", "create");
		return "room-cu";
	}

	@PostMapping("/create")
	public String postCreateRoom(
			@AuthenticationPrincipal UserDetails user,
			@Valid Room room,
			BindingResult result,
			Model model
	) {
		if (result.hasErrors()) {
			model.addAttribute("cu", "create");
			return "room-cu";
		}
		System.out.println(room.getName());
		roomManager.addRoom(room);
		return "redirect:/rooms";
	}
}