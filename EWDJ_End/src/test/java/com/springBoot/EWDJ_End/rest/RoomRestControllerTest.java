package com.springBoot.EWDJ_End.rest;

import static com.springBoot.EWDJ_End.rest.API_BASE_PATHS.ROOMS_URI;
import static init.InitRoom.OK_CAPACITY;
import static init.InitRoom.OK_NAME;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import domain.LocaleException;
import domain.room.Room;
import domain.room.RoomManager;

@SpringBootTest
class RoomRestControllerTest {
	@Mock
	private RoomManager roomManager;
	private RoomRestController controller;
	private MockMvc mockMvc;

	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new RoomRestController(roomManager);
		mockMvc = standaloneSetup(controller).build();
	}

	private Room anRoom(String name, int capacity) {
		return new Room(name, capacity);
	}

	@Test
	public void testGetRoom_isOk() throws Exception {
		Mockito.when(roomManager.giveRoom(OK_NAME)).thenReturn(anRoom(OK_NAME, OK_CAPACITY));

		String uri = "%s/%s".formatted(ROOMS_URI, OK_NAME);

		mockMvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(OK_NAME))
				.andExpect(jsonPath("$.capacity").value(OK_CAPACITY));

		Mockito.verify(roomManager).giveRoom(OK_NAME);
	}

	@Test
	public void testGetRoom_notFound() throws Exception {
		Mockito.when(roomManager.giveRoom(OK_NAME)).thenThrow(
				new LocaleException("RoomManager.giveRoom.existsByName", new Object[] { OK_NAME })
		);

		String uri = "%s/%s".formatted(ROOMS_URI, OK_NAME);

		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(get(uri)).andReturn();
		});

		assertTrue(exception.getCause() instanceof LocaleException);

		Mockito.verify(roomManager).giveRoom(OK_NAME);
	}
}
