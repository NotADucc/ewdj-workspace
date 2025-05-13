package com.springBoot.EWDJ_End.rest;

import static com.springBoot.EWDJ_End.rest.API_BASE_PATHS.ROOMS_URI;
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

	private final String NAME = "A123";
	private final int CAPACITY = 1;

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
		Mockito.when(roomManager.giveRoom(NAME)).thenReturn(anRoom(NAME, CAPACITY));

		String uri = "%s/%s".formatted(ROOMS_URI, NAME);

		mockMvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(NAME))
				.andExpect(jsonPath("$.capacity").value(CAPACITY));
		
		Mockito.verify(roomManager).giveRoom(NAME);
	}

	@Test
	public void testGetRoom_notFound() throws Exception {
		Mockito.when(roomManager.giveRoom(NAME)).thenThrow(new LocaleException("RoomManager.giveRoom.existsByName", new Object[] { NAME }));

		String uri = "%s/%s".formatted(ROOMS_URI, NAME);

		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(get(uri)).andReturn();
	    });
				
		assertTrue(exception.getCause() instanceof LocaleException);
		
		Mockito.verify(roomManager).giveRoom(NAME);
	}
}
