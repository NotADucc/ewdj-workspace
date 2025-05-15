package com.springBoot.EWDJ_End.rest;

import static com.springBoot.EWDJ_End.rest.API_BASE_PATHS.ROOMS_URI;
import static init.InitRoom.OK_CAPACITY;
import static init.InitRoom.OK_NAME;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import domain.LocaleException;
import domain.room.IRoomRepository;
import domain.room.Room;
import domain.room.RoomManager;

@SpringBootTest
class RoomRestControllerTest {
	
	private IRoomRepository roomRepository;
	private RoomRestController controller;
	private MockMvc mockMvc;

	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		
        roomRepository = mock(IRoomRepository.class);

		controller = new RoomRestController(new RoomManager(roomRepository));
		mockMvc = standaloneSetup(controller).build();
	}

	private Room anRoom(String name, int capacity) {
		return new Room(name, capacity);
	}

	@Test
	public void testGetRoom_isOk() throws Exception {
		Mockito.when(roomRepository.existsByName(OK_NAME)).thenReturn(true);
		Mockito.when(roomRepository.giveRoom(OK_NAME)).thenReturn(anRoom(OK_NAME, OK_CAPACITY));
		

		String uri = "%s/%s".formatted(ROOMS_URI, OK_NAME);

		mockMvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(OK_NAME))
				.andExpect(jsonPath("$.capacity").value(OK_CAPACITY));

		Mockito.verify(roomRepository).existsByName(OK_NAME);
		Mockito.verify(roomRepository).giveRoom(OK_NAME);
	}

	@Test
	public void testGetRoom_notFound() throws Exception {
		Mockito.when(roomRepository.existsByName(OK_NAME)).thenReturn(false);

		String uri = "%s/%s".formatted(ROOMS_URI, OK_NAME);

		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(get(uri)).andReturn();
		});

		assertTrue(exception.getCause() instanceof LocaleException);

		Mockito.verify(roomRepository).existsByName(OK_NAME);
	}
}
