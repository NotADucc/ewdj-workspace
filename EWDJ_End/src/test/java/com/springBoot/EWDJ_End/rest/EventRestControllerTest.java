package com.springBoot.EWDJ_End.rest;

import static com.springBoot.EWDJ_End.rest.API_BASE_PATHS.EVENTS_URI;
import static com.springBoot.EWDJ_End.rest.API_BASE_PATHS.ROOMS_URI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static utils.InitFormatter.FORMATTER;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import domain.event.Event;
import domain.event.EventManager;
import domain.room.Room;

@SpringBootTest
class EventRestControllerTest {
	@Mock
	private EventManager eventManager;
	private EventRestController controller;
	private MockMvc mockMvc;

	private final int ID = 1234;
	private final String NAME = "Test";
	private final String DESCRIPTION = "Description";
	private final Room ROOM = new Room("a123", 5);
	private final LocalDateTime TIME = LocalDateTime.now();
	private final String EXPECTED_TIME_FORMATED = FORMATTER.format(TIME);
	private final String B_CODE = "0000";
	private final int B_CHECK = 0;
	private final Double PRICE = 10.0;
	private final List<String> SPEAKERS = List.of("Jeff");

	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new EventRestController(eventManager);
		mockMvc = standaloneSetup(controller).build();
	}

	private List<Event> anEvents(
			int id,
			String name,
			String description,
			Room room,
			LocalDateTime time,
			String b_code,
			int b_check,
			Double price,
			List<String> speakers
	) {
		return List
				.of(anEvent(id, name, description, room, time, b_code, b_check, price, speakers));
	}

	private Event anEvent(
			int id,
			String name,
			String description,
			Room room,
			LocalDateTime time,
			String b_code,
			int b_check,
			Double price,
			List<String> speakers
	) {
		return new Event(id, name, description, room, time, b_code, b_check, price, speakers);
	}

	@Test
	public void testGetEvents_isOk() throws Exception {
		Mockito.when(eventManager.getEventsOnDate(TIME.toLocalDate())).thenReturn(
				anEvents(ID, NAME, DESCRIPTION, ROOM, TIME, B_CODE, B_CHECK, PRICE, SPEAKERS)
		);

		String uri = "%s?date=%s".formatted(EVENTS_URI, TIME.toLocalDate());

		mockMvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(ID))
				.andExpect(jsonPath("$[0].name").value(NAME))
				.andExpect(jsonPath("$[0].roomURI").value(ROOMS_URI + "/" + ROOM.getName()))
				.andExpect(jsonPath("$[0].dateTime").value(EXPECTED_TIME_FORMATED))
				.andExpect(jsonPath("$[0].price").value(PRICE))
				.andExpect(jsonPath("$[0].speakers[0]").value(SPEAKERS.getFirst()));
		Mockito.verify(eventManager).getEventsOnDate(TIME.toLocalDate());
	}

	@Test
	public void testGetEvents_emptyList() throws Exception {
		Mockito.when(eventManager.getEventsOnDate(TIME.toLocalDate()))
				.thenReturn(new ArrayList<>());
		
		String uri = "%s?date=%s".formatted(EVENTS_URI, TIME.toLocalDate());
		
		mockMvc.perform(get(uri)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		Mockito.verify(eventManager).getEventsOnDate(TIME.toLocalDate());
	}

}
