package com.example.EWDJ_End.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI)
public class AdminRoomController {
	private final MessageSource messageSource;
	private final RoomManager roomManager;

	@GetMapping
	public String getRooms(Model model) {
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

		roomManager.addRoom(room);

		String localizedMsg = messageSource.getMessage(
				"RoomController.postCreateRoom.succes",
				new Object[] { room.getCapacity() },
				LocaleContextHolder.getLocale()
		);

		model.addAttribute("msg", localizedMsg);
		return "room-cu";
	}
}