package com.springBoot.EWDJ_End.controller;

import static init.InitEvent.OK_B_CHECK;
import static init.InitEvent.OK_B_CODE;
import static init.InitEvent.OK_DESCRIPTION;
import static init.InitEvent.OK_EVENT;
import static init.InitEvent.OK_ID;
import static init.InitEvent.OK_NAME;
import static init.InitEvent.OK_PRICE;
import static init.InitEvent.OK_ROOM;
import static init.InitEvent.OK_SPEAKERS;
import static init.InitEvent.OK_TIME;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.event.Event;
import domain.event.IEventRepository;
import domain.room.IRoomRepository;
import domain.room.Room;
import domain.user.IUserRepository;
import init.InitRoom;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminEventControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
    private IEventRepository eventRepository;
	@MockitoBean 
	private IRoomRepository roomRepository;
	@MockitoBean 
	private IUserRepository userRepository;
	
	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testGetCreateEvent() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("event-cu"))
				.andExpect(model().attributeExists("event", "roomList", "cu"));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testGetCreateEvent_user_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	void testGetCreateEvent_anonymous_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testPostCreateEvent() throws Exception {
		when(eventRepository.doesEventExistOnSpecificDay(OK_EVENT)).thenReturn(false);
		when(roomRepository.isRoomBooked(OK_EVENT.getRoom(), OK_EVENT.getDateTime())).thenReturn(false);
		doNothing().when(eventRepository).addEvent(OK_EVENT);
		
		mockMvc.perform(post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create")
						.flashAttr("event", OK_EVENT)
						.with(csrf())
				).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/events"));

		verify(eventRepository).doesEventExistOnSpecificDay(OK_EVENT);
		verify(roomRepository).isRoomBooked(OK_EVENT.getRoom(), OK_EVENT.getDateTime());
		verify(eventRepository).addEvent(OK_EVENT);
	}

	@ParameterizedTest
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@MethodSource("testPostCreateEvent_incorrect_stream")
	void testPostCreateEvent_incorrect(
			int id,
			String name,
			String description,
			Room room,
			LocalDateTime time,
			String code,
			int check,
			Double price,
			List<String> speakers,
			String[] fieldErrors
	) throws Exception {
		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create").flashAttr(
						"event",
						new Event(id, name, description, room, time, code, check, price, speakers)
				).with(csrf())
		).andExpect(status().isOk()).andExpect(view().name("event-cu"))
				.andExpect(model().attributeExists("roomList", "cu"))
				.andExpect(model().attributeHasFieldErrors("event", fieldErrors));
	}

	static Stream<Arguments> testPostCreateEvent_incorrect_stream() {
		return Stream.of(
				// name
				Arguments.of(
						OK_ID,
						null,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						" ",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"\n",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"\t",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"1 Appel",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				// room
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						null,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"room"}
				),
				//time
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						null,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"dateTime"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						LocalDateTime.now().plusYears(-10),
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"dateTime"}
				),
				//beamercode
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						null,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						" ",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"\n",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"\t",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"\t",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"00000",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"ABCD",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				//beamercheck
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK - 1,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK + 1,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercheck"}
				),
				// price
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						null,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						0.0,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						9.98,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						100.0,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						100.001,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				//speakers
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						null,
						new String[] {"speakers"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						List.of(),
						new String[] {"speakers"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						List.of("1", "1"),
						new String[] {"speakers"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						List.of("1", "2", "3", "4"),
						new String[] {"speakers"}
				)
		);
	}
	
	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testPostCreateEvent_user_noAccess() throws Exception {
		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create")
					.flashAttr("event", new Event()
				).with(csrf())
		).andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	void testPostCreateEvent_anonymous_noAccess() throws Exception {
		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/create")
					.flashAttr("event", new Event()
				).with(csrf())
			).andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testGetEditEvent() throws Exception {
		when(eventRepository.doesEventExist(OK_ID)).thenReturn(true);
		when(eventRepository.getEvent(OK_ID)).thenReturn(OK_EVENT);
		
		when(roomRepository.existsByName(OK_EVENT.getRoom().getName())).thenReturn(true);
		when(roomRepository.giveRoom(OK_EVENT.getRoom().getName())).thenReturn(OK_ROOM);
		when(roomRepository.giveRooms()).thenReturn(List.of(InitRoom.OK_ROOM));
		
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/%s/edit".formatted(OK_ID)))
				.andExpect(status().isOk())
				.andExpect(view().name("event-cu"))
				.andExpect(model().attributeExists("event", "roomList", "cu"));
		
		verify(eventRepository).doesEventExist(OK_ID);
		verify(eventRepository).getEvent(OK_ID);
		verify(roomRepository).existsByName(OK_EVENT.getRoom().getName());
		verify(roomRepository).giveRoom(OK_EVENT.getRoom().getName());
		verify(roomRepository).giveRooms();
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testGetEditEvent_user_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/1/edit"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	void testGetEditEvent_anonymous_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/1/edit"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}
	@ParameterizedTest
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@MethodSource("testPostEditEvent_incorrect_stream")
	void testPostEditEvent_incorrect(
			int id,
			String name,
			String description,
			Room room,
			LocalDateTime time,
			String code,
			int check,
			Double price,
			List<String> speakers,
			String[] fieldErrors
	) throws Exception {
		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/%s/edit".formatted(id)).flashAttr(
						"event",
						new Event(id, name, description, room, time, code, check, price, speakers)
				).with(csrf())
		).andExpect(status().isOk()).andExpect(view().name("event-cu"))
				.andExpect(model().attributeExists("roomList", "cu"))
				.andExpect(model().attributeHasFieldErrors("event", fieldErrors));
	}

	static Stream<Arguments> testPostEditEvent_incorrect_stream() {
		return Stream.of(
				// name
				Arguments.of(
						OK_ID,
						null,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						" ",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"\n",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"\t",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				Arguments.of(
						OK_ID,
						"1 Appel",
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"name"}
				),
				// room
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						null,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"room"}
				),
				//time
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						null,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"dateTime"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						LocalDateTime.now().plusYears(-10),
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"dateTime"}
				),
				//beamercode
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						null,
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						" ",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"\n",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"\t",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"\t",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"00000",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						"ABCD",
						OK_B_CHECK,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercode", "beamercheck"}
				),
				//beamercheck
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK - 1,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercheck"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK + 1,
						OK_PRICE,
						OK_SPEAKERS,
						new String[] {"beamercheck"}
				),
				// price
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						null,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						0.0,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						9.98,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						100.0,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						100.001,
						OK_SPEAKERS,
						new String[] {"price"}
				),
				//speakers
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						null,
						new String[] {"speakers"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						List.of(),
						new String[] {"speakers"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						List.of("1", "1"),
						new String[] {"speakers"}
				),
				Arguments.of(
						OK_ID,
						OK_NAME,
						OK_DESCRIPTION,
						OK_ROOM,
						OK_TIME,
						OK_B_CODE,
						OK_B_CHECK,
						OK_PRICE,
						List.of("1", "2", "3", "4"),
						new String[] {"speakers"}
				)
		);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testPostEditEvent() throws Exception {
		when(eventRepository.doesEventExist(OK_ID)).thenReturn(true);
		when(eventRepository.getAllEventsOnSpecificDate(OK_EVENT.getDateTime().toLocalDate()))
			.thenReturn(List.of(OK_EVENT));
		when(eventRepository.getEvent(OK_ID)).thenReturn(OK_EVENT);
		doNothing().when(eventRepository).editEvent(OK_EVENT);
		
		
		mockMvc.perform(post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/%s/edit".formatted(OK_ID))
					.flashAttr("event", OK_EVENT)
					.flashAttr("roomList", List.of(InitRoom.OK_ROOM))
					.with(csrf())
				).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/events/" + OK_ID));
		
		verify(eventRepository).doesEventExist(OK_ID);
		verify(eventRepository).getAllEventsOnSpecificDate(OK_EVENT.getDateTime().toLocalDate());
		verify(eventRepository).getEvent(OK_ID);
		verify(eventRepository).editEvent(OK_EVENT);
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testPostEditEvent_user_noAccess() throws Exception {
		mockMvc.perform(post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/1/edit"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	void testPostEditEvent_anonymous_noAccess() throws Exception {
		mockMvc.perform(post(BASE_PATHS.ADMIN_URI + BASE_PATHS.EVENTS_URI + "/1/edit")
					.flashAttr("event", OK_EVENT)
					.flashAttr("roomList", List.of(InitRoom.OK_ROOM))
					.with(csrf())
				).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}
}