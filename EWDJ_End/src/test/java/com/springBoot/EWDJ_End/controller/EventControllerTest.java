package com.springBoot.EWDJ_End.controller;

import static init.InitEvent.OK_EVENT;
import static init.InitEvent.OK_ID;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import domain.user.IUserRepository;
import domain.user.User;
import init.InitEvent;
import init.InitUser;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
    private IEventRepository eventRepository;
	@MockitoBean 
	private IRoomRepository roomRepository;
	@MockitoBean 
	private IUserRepository userRepository;
	
	@Test
	void testGetEvents() throws Exception {
		mockMvc.perform(get(BASE_PATHS.EVENTS_URI))
				.andExpect(status().isOk())
				.andExpect(view().name("event-overview"))
				.andExpect(model().attributeExists("eventList"));
	}
	
	static Stream<Arguments> testGetEvent_stream() {
	    return Stream.of(
	    		Arguments.of(InitUser.OK_NAME, new String[]{"USER"}, InitUser.OK_USER),
	    		Arguments.of(InitUser.OK_NAME, new String[]{"ADMIN"}, InitUser.OK_ADMIN)
	    );
	} 
	
	@ParameterizedTest
	@MethodSource("testGetEvent_stream")
	void testGetEvent(String username, String[] roles, User user) throws Exception {
		when(eventRepository.doesEventExist(InitEvent.OK_ID)).thenReturn(true);
		when(eventRepository.getEvent(InitEvent.OK_ID)).thenReturn(OK_EVENT);
		when(userRepository.doesUserExist(username)).thenReturn(true);
		when(userRepository.getUserByUsername(username)).thenReturn(user);
		
		mockMvc.perform(get(BASE_PATHS.EVENTS_URI + "/%s".formatted(InitEvent.OK_ID))
					.with(user(username).roles(roles))
				).andExpect(status().isOk())
				.andExpect(view().name("event-details"))
				.andExpect(model().attributeExists("event", "isFavorited", "favoriteCountExceeded"));
		
		verify(eventRepository, times(2)).doesEventExist(InitEvent.OK_ID);
		verify(eventRepository, times(1)).getEvent(InitEvent.OK_ID);
		verify(userRepository, times(2)).doesUserExist(username);
		verify(userRepository, times(2)).getUserByUsername(username);
	}
		
	@Test
	@WithAnonymousUser
	void testGetEvent_anonymous_noAcces() throws Exception {
		mockMvc.perform(get(BASE_PATHS.EVENTS_URI + "/%s".formatted(OK_ID)))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = InitUser.OK_NAME, roles = {"USER"})
	void testPostToggleFavorite() throws Exception {
		when(eventRepository.doesEventExist(OK_ID)).thenReturn(true);
		when(eventRepository.getEvent(OK_ID)).thenReturn(OK_EVENT);
		when(userRepository.doesUserExist(InitUser.OK_NAME)).thenReturn(true);
		when(userRepository.getUserByUsername(InitUser.OK_NAME)).thenReturn(InitUser.OK_USER);
		doNothing().when(eventRepository).favorite(InitEvent.OK_ID, InitUser.OK_ID);
		
		mockMvc.perform(post(BASE_PATHS.EVENTS_URI + "/%s/favorite".formatted(OK_ID))
					.with(csrf())
				).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/events/%s".formatted(OK_ID)));
		
		verify(eventRepository, times(1)).doesEventExist(OK_ID);
		verify(eventRepository, times(1)).getEvent(OK_ID);
		verify(userRepository, times(1)).doesUserExist(InitUser.OK_NAME);
		verify(userRepository, times(1)).getUserByUsername(InitUser.OK_NAME);
		verify(eventRepository, times(1)).favorite(InitEvent.OK_ID, InitUser.OK_ID);
		verify(eventRepository, times(0)).unfavorite(InitEvent.OK_ID, InitUser.OK_ID);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	void testPostToggleFavorite_admin_noAccess() throws Exception {
		mockMvc.perform(post(BASE_PATHS.EVENTS_URI + "/%s/favorite".formatted(OK_ID)).with(csrf()))
				.andExpect(status().isForbidden());
	}
	
	@Test
	@WithAnonymousUser
	void testPostToggleFavorite_anonymous_noAccess() throws Exception {
		mockMvc.perform(post(BASE_PATHS.EVENTS_URI + "/%s/favorite".formatted(OK_ID)).with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}
}