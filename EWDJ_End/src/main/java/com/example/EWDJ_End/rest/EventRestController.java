package com.example.EWDJ_End.rest;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import domain.event.EventManager;
import dto.mapper.EventOutputDTOMapper;
import dto.model.EventOutputDTO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(API_BASE_PATHS.EVENTS_URI)
public class EventRestController {
	private final EventManager eventManager;

	@GetMapping
	public Collection<EventOutputDTO> getEvent(@RequestParam LocalDate date) {
		return EventOutputDTOMapper.toDTO(eventManager.getEventsOnDate(date));
	}
}
