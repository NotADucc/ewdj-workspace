package com.springBoot.EWDJ_End.rest;

import static com.springBoot.EWDJ_End.rest.API_BASE_PATHS.EVENTS_URI;
import static com.springBoot.EWDJ_End.rest.API_BASE_PATHS.ROOMS_URI;
import static init.InitEvent.OK_B_CHECK;
import static init.InitEvent.OK_B_CODE;
import static init.InitEvent.OK_DESCRIPTION;
import static init.InitEvent.OK_ID;
import static init.InitEvent.OK_NAME;
import static init.InitEvent.OK_PRICE;
import static init.InitEvent.OK_ROOM;
import static init.InitEvent.OK_SPEAKERS;
import static init.InitEvent.OK_TIME;
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

	private final String EXPECTED_TIME_FORMATED = FORMATTER.format(OK_TIME);


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
		Mockito.when(eventManager.getEventsOnDate(OK_TIME.toLocalDate())).thenReturn(
				anEvents(OK_ID, OK_NAME, OK_DESCRIPTION, OK_ROOM, OK_TIME, OK_B_CODE, OK_B_CHECK, OK_PRICE, OK_SPEAKERS)
		);

		String uri = "%s?date=%s".formatted(EVENTS_URI, OK_TIME.toLocalDate());

		mockMvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(OK_ID))
				.andExpect(jsonPath("$[0].name").value(OK_NAME))
				.andExpect(jsonPath("$[0].roomURI").value(ROOMS_URI + "/" + OK_ROOM.getName()))
				.andExpect(jsonPath("$[0].dateTime").value(EXPECTED_TIME_FORMATED))
				.andExpect(jsonPath("$[0].price").value(OK_PRICE))
				.andExpect(jsonPath("$[0].speakers[0]").value(OK_SPEAKERS.getFirst()));
		Mockito.verify(eventManager).getEventsOnDate(OK_TIME.toLocalDate());
	}

	@Test
	public void testGetEvents_emptyList() throws Exception {
		Mockito.when(eventManager.getEventsOnDate(OK_TIME.toLocalDate()))
				.thenReturn(new ArrayList<>());
		
		String uri = "%s?date=%s".formatted(EVENTS_URI, OK_TIME.toLocalDate());
		
		mockMvc.perform(get(uri)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		Mockito.verify(eventManager).getEventsOnDate(OK_TIME.toLocalDate());
	}

}
