package com.springBoot.EWDJ_End.controller;

import static init.InitEvent.OK_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.user.IUserRepository;
import init.InitEvent;
import init.InitUser;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean 
	private IUserRepository userRepository;
	
	@Test
	@WithMockUser(username = InitUser.OK_NAME, roles = {"USER"})
	void testGetFavorites() throws Exception {	
		when(userRepository.getFavoriteEvents(InitUser.OK_NAME)).thenReturn(List.of(InitEvent.OK_EVENT));
		
		mockMvc.perform(get(BASE_PATHS.USERS_URI + "/favorite/events")
				).andExpect(status().isOk())
				.andExpect(view().name("event-favorites"))
				.andExpect(model().attributeExists("eventList"));
		
		verify(userRepository).getFavoriteEvents(InitUser.OK_NAME);
	}
		
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testGetFavorites_admin_noAcces() throws Exception {
		mockMvc.perform(get(BASE_PATHS.USERS_URI + "/%s".formatted(OK_ID)))
				.andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	void testGetFavorites_anonymous_noAcces() throws Exception {
		mockMvc.perform(get(BASE_PATHS.USERS_URI + "/%s".formatted(OK_ID)))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}
}