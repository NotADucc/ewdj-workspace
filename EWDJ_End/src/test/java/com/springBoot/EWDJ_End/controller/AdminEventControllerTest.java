package com.springBoot.EWDJ_End.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import domain.event.Event;
import domain.event.EventManager;
import domain.room.Room;
import domain.room.RoomManager;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminEventControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private EventManager eventManager;
	@Mock
	private RoomManager roomManager;

	private Event EVENT;

	@BeforeEach
	void setup() {
		final int ID = 1234;
		final String NAME = "Test";
		final String DESCRIPTION = "Description";
		final Room ROOM = new Room("a123", 5);
		final LocalDateTime TIME = LocalDateTime.now();
		final String B_CODE = "0097";
		final int B_CHECK = 0;
		final Double PRICE = 10.0;
		final List<String> SPEAKERS = List.of("Jeff");
		EVENT = new Event(ID, NAME, DESCRIPTION, ROOM, TIME, B_CODE, B_CHECK, PRICE, SPEAKERS);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testGetCreateEvent() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create"))
				.andExpect(status().isOk()).andExpect(view().name("event-cu"))
				.andExpect(model().attributeExists("event", "roomList", "cu"));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testGetCreateEvent_user_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create"))
				.andExpect(status().isForbidden());
	}

	@Test
	void testGetCreateEvent_anonymous_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testPostCreateEvent() throws Exception {
		//doNothing().when(eventManager).addEvent(EVENT);

		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create")
						.flashAttr("event", EVENT)
						.with(csrf())
		).andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/events"));

		//verify(eventManager).addEvent(EVENT);
	}

	// @Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@MethodSource("testPostCreateEvent_incorrect_stream")
	void testPostCreateEvent_incorrect() throws Exception {
		mockMvc.perform(post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create"))
				.andExpect(status().isOk()).andExpect(view().name("event-cu"))
				.andExpect(model().attributeExists("roomList", "cu"))
				.andExpect(model().attributeHasFieldErrors("bankCustomer", "id"));
	}

	Stream<Arguments> testPostCreateEvent_incorrect_stream() {
		return Stream.of(Arguments.of());
	}
}