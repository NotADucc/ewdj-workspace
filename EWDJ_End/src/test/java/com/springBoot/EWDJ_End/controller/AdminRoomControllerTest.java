package com.springBoot.EWDJ_End.controller;

import static init.InitRoom.OK_CAPACITY;
import static init.InitRoom.OK_NAME;
import static init.InitRoom.OK_ROOM;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import domain.event.IEventRepository;
import domain.room.IRoomRepository;
import domain.room.Room;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminRoomControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	// i need this repo for some reason or else my test crash
	@MockitoBean
    private IEventRepository eventRepository;
	@MockitoBean 
	private IRoomRepository roomRepository;
	
	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testGetRooms() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI))
				.andExpect(status().isOk())
				.andExpect(view().name("room-overview"))
				.andExpect(model().attributeExists("roomList"));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testGetCreateEvent_user_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	void testGetCreateEvent_anonymous_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testGetCreateRoom() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI + "/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("room-cu"))
				.andExpect(model().attributeExists("room", "cu"));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testGetCreateRoom_user_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI + "/create"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	void testGetCreateRoom_anonymous_noAccess() throws Exception {
		mockMvc.perform(get(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI + "/create"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testPostCreateRoom() throws Exception {
		when(roomRepository.existsByName(OK_NAME)).thenReturn(false);
		doNothing().when(roomRepository).addRoom(OK_ROOM);
		
		mockMvc.perform(post(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI + "/create")
						.flashAttr("room", OK_ROOM)
						.with(csrf())
				).andExpect(status().isOk())
				.andExpect(view().name("room-cu"))
				.andExpect(model().attributeExists("room", "msg"));

		verify(roomRepository).existsByName(OK_NAME);
		verify(roomRepository).addRoom(OK_ROOM);
	}
	
	@ParameterizedTest
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@MethodSource("testPostCreateRoom_incorrect_stream")
	void testPostCreateRoom_incorrect(
			String name,
			int capacity,
			String[] fieldErrors
	) throws Exception {
		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI + "/create").flashAttr(
						"room",
						new Room(name, capacity)
				).with(csrf())
		).andExpect(status().isOk())
				.andExpect(view().name("room-cu"))
				.andExpect(model().attributeExists("cu"))
				.andExpect(model().attributeHasFieldErrors("room", fieldErrors));
	}

	static Stream<Arguments> testPostCreateRoom_incorrect_stream() {
		return Stream.of(
				// name
				Arguments.of(
						null,
						OK_CAPACITY,
						new String[] {"name"}
				),
				Arguments.of(
						"",
						OK_CAPACITY,
						new String[] {"name"}
				),
				Arguments.of(
						"\n",
						OK_CAPACITY,
						new String[] {"name"}
				),
				Arguments.of(
						"\t",
						OK_CAPACITY,
						new String[] {"name"}
				),
				Arguments.of(
						"1111",
						OK_CAPACITY,
						new String[] {"name"}
				),
				Arguments.of(
						"1AAA",
						OK_CAPACITY,
						new String[] {"name"}
				),
				Arguments.of(
						"A1",
						OK_CAPACITY,
						new String[] {"name"}
				),
				Arguments.of(
						"A11",
						OK_CAPACITY,
						new String[] {"name"}
				),
				// capacity
				Arguments.of(
						OK_NAME,
						0,
						new String[] {"capacity"}
				),
				Arguments.of(
						OK_NAME,
						51,
						new String[] {"capacity"}
				)
		);
	}
	
	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void testPostCreateRoom_user_noAccess() throws Exception {
		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI + "/create").flashAttr(
						"room",
						new Room()
				).with(csrf())
		).andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	void testPostCreateRoom_anonymous_noAccess() throws Exception {
		mockMvc.perform(
				post(BASE_PATHS.ADMIN_URI + BASE_PATHS.ROOMS_URI + "/create").flashAttr(
						"room",
						new Room()
				).with(csrf())
			).andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("**/login"));
	}
}