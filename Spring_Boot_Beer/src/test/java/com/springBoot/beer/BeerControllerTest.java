package com.springBoot.beer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.ExpertBean;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ExpertBean expertBean;

	@Test
	void testBeerGet() throws Exception {
		mockMvc.perform(get("/beer")).andExpect(status().isOk()).andExpect(view().name("formView"))
				.andExpect(model().attributeExists("colorsList"))
				.andExpect(model().attributeExists("beerCommand"));
	}

	@Test
	void testBeerPost() throws Exception {
		var expResult = List.of("Hoegaarden", "Brugs Witbier");
		var color = "light";
		
		when(expertBean.getExpert(color)).thenReturn(expResult);

		mockMvc.perform(post("/beer").flashAttr("beerCommand", new BeerCommand(color)))
				.andExpect(status().isOk()).andExpect(view().name("beerView"))
				.andExpect(model().attributeExists("selectedColor"))
				.andExpect(model().attributeExists("beerList"))
				.andExpect(model().attribute("selectedColor", color))
				.andExpect(model().attribute("beerList", expResult));
	}
}