package com.springBoot.listSpelStart;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.RequestDispatcher;

@WebMvcTest(NotFoundController.class)
public class NotFoundControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHandleErrorNotFound() throws Exception {
		mockMvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404))
				.andExpect(status().isOk()).andExpect(view().name("error/notFound"));
	}

	@Test
	public void testHandleErrorOtherErrorCodes() throws Exception {
		mockMvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/game"));
	}

	@Test
	public void testHandleErrorWithoutErrorCode() throws Exception {
		mockMvc.perform(get("/error")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/game"));
	}

}