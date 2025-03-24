package com.springBoot.exceptions;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import exception.CustomGenericException;
import exception.ReservationNotAvailableException;
import service.WelcomeService;

@WebMvcTest(WelcomeController.class)
@Import(SpringBootExceptionsApplication.class)
class WelcomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private WelcomeService mockService;

	@Test
	void testGetRequest() throws Exception {
		mockMvc.perform(get("/welcome")).andExpect(view().name("welcome"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("today"))
				.andExpect(model().attributeExists("handlingTime"));
	}

	@Test
	void testReservationNotAvailableException() throws Exception {
		doThrow(new ReservationNotAvailableException("test", LocalDate.of(34, 10, 10), 1))
				.when(mockService).example();
		mockMvc.perform(get("/welcome")).andExpect(view().name("error/reservationNotAvailable"));
	}

	@Test
	void testNumberFormatException() throws Exception {
		doThrow(new NumberFormatException()).when(mockService).example();
		mockMvc.perform(get("/welcome")).andExpect(view().name("error/generic_error"))
				.andExpect(model().attributeDoesNotExist("errCode"))
				.andExpect(model().attributeDoesNotExist("errMsg"));
	}

	@Test
	void testCustomGenericException() throws Exception {
		doThrow(new CustomGenericException("TEST", "This is custom test message")).when(mockService)
				.example();
		mockMvc.perform(get("/welcome")).andExpect(view().name("error/generic_error"))
				.andExpect(model().attributeExists("errCode"))
				.andExpect(model().attributeExists("errMsg"));
	}

	@Test
	void testIllegalArgumentException() throws Exception {
		doThrow(new IllegalArgumentException()).when(mockService).example();
		mockMvc.perform(get("/welcome")).andExpect(view().name("error/error"));
	}
}
