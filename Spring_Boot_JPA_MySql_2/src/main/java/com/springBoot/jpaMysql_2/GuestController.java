package com.springBoot.jpaMysql_2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.ComputerRepository;
import repository.GuestRepository;

@Controller
@RequestMapping("/guest")
public class GuestController {

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private ComputerRepository computerRepository;

	@GetMapping
	public String listGuest(Model model) {

		model.addAttribute("guestList", guestRepository.findAll());
		model.addAttribute("guestName", guestRepository.findByName("Blondeel"));
		model.addAttribute("guestFirstname", guestRepository.findByFirstname("Sandra"));

		model.addAttribute("guestList2", guestRepository.findByNameStartingWith("blon"));
		
		model.addAttribute("guestList3", guestRepository.findByNameStartingWith2("k"));
		
		model.addAttribute("computerList", computerRepository.findAll());
		
		return "guest";
	}
}
