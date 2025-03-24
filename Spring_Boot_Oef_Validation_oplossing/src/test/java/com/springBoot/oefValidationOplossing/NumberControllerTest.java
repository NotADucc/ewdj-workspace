package com.springBoot.oefValidationOplossing;

import static init.InitNumbers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import domain.Numbers;

@WebMvcTest(NumberController.class)
@Import(SpringBootOefValidationOplossingApplication.class)
class NumberControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetRequest() throws Exception {

		mockMvc.perform(get("/numbers"))
		.andExpect(view().name("numberForm"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("numbers"));
	}
	
	 @Test
	 public void testPostRequestValidNumbers() throws Exception {
		
		Numbers validNumbers = Numbers.builder().amount(OK_AMOUNT).number1(OK_NUMBER1).number2(OK_NUMBER2).build();
		
        mockMvc.perform(post("/numbers").flashAttr("numbers", validNumbers))
                .andExpect(status().isOk())
                .andExpect(view().name("numberSuccess"))
                .andExpect(model().attributeExists("numbers"));
    }

	 private static Stream<Arguments> invalidNumbersData() {
	        return Stream.of(
	            Arguments.of(6000.05, OK_NUMBER1, OK_NUMBER2, new String[]{"amount"}), 
	            Arguments.of(OK_AMOUNT, 500, 50000, new String[]{"number1"}),
	            Arguments.of(5500.50, 0, null, new String[]{"amount", "number1", "number2"})
	        );
	 }
	 
	 @ParameterizedTest
	 @MethodSource("invalidNumbersData")
	 public void testPostRequestInvalidNumbers(Double amount, Integer number1, Integer number2, String[] expectedErrors) throws Exception {
	   
		Numbers invalidNumbers = Numbers.builder().amount(amount).number1(number1).number2(number2).build();
			
        mockMvc.perform(post("/numbers").flashAttr("numbers", invalidNumbers))
                .andExpect(status().isOk())
                .andExpect(view().name("numberForm"))
                .andExpect(model().attributeHasFieldErrors("numbers", expectedErrors));
    }


}
