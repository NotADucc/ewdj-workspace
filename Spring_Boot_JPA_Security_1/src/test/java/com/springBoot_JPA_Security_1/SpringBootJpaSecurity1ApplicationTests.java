package com.springBoot_JPA_Security_1;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.MyUser;
import domain.Role;
import repository.UserRepository;

@Import(SecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootJpaSecurity1ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
    private UserDetailsService userService;

	@MockitoBean
    private UserRepository userRepository;
    
    @BeforeEach
    public void setup() {

        // Mocking a MyUser
        MyUser normalUser = MyUser.builder()
                .username("user")
                .password("password")
                .role(Role.USER)
                .city("User City")
                .build();
        
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
	    User user= new User(normalUser.getUsername(), normalUser.getPassword(), Collections.singletonList(authority));
       
	    when(userService.loadUserByUsername("user")).thenReturn(user);
        when(userRepository.findByUsername("user")).thenReturn(normalUser);
    }
    
	@Test
	public void loginGet() throws Exception {
	    	mockMvc.perform(get("/login"))
	        .andExpect(status().isOk())
	    	.andExpect(view().name("login"));
	}
	
	@Test
	public void accessDeniedPageGet() throws Exception {
	    	mockMvc.perform(get("/403"))
	        .andExpect(status().isOk())
	    	.andExpect(view().name("403"));
	}
	 
	@Test
	@WithMockUser(username = "user")
	public void testAccessWithUserRole() throws Exception {
		 mockMvc.perform(get("/welcome"))
	        .andExpect(status().isOk())
			.andExpect(view().name("hello"))
			.andExpect(model().attributeExists("username"))
			.andExpect(model().attributeExists("city"))
			.andExpect(model().attribute("username", "user"));
    }
	
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	@Test
    public void testNoAccess() throws Exception {
        mockMvc.perform(get("/welcome"))
            .andExpect(status().isForbidden());
	}
	
	@WithAnonymousUser
	@Test
	public void testNoAccessAnonymous() throws Exception {
	    mockMvc.perform(get("/welcome"))
	            .andExpect(redirectedUrlPattern("**/login"));
	}

}
