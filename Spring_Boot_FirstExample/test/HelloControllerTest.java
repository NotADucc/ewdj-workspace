package com.springBoot_firstExample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.HelloService;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private HelloService mockService;

	@Test
	void testHelloGet() throws Exception {

		mockMvc.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(view().name("nameForm"))
			.andExpect(model().attributeExists("name"));
	}

	@Test
	void testHelloPost() throws Exception {
		String expResult = "Hello testMock!";
		when(mockService.sayHello("test")).thenReturn(expResult);

		mockMvc.perform(post("/hello").flashAttr("name", new Name("test")))
				                 // OR
				                   // .param("value", "test")
			.andExpect(status().isOk())
			.andExpect(view().name("helloView")).andExpect(model().attributeExists("helloMessage"))
			.andExpect(model().attribute("helloMessage", expResult));
	}
}