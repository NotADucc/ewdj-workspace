package com.example.EWDJ_End.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String showEventDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("event", eventManager.giveEvent(id));
        return "event-details";
    }
}