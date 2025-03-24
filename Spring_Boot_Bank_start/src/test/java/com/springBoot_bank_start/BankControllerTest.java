package com.springBoot_bank_start;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import domain.BankCustomer;
import service.BankCustomerService;

@WebMvcTest(BankController.class)
class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BankCustomerService bankService;

	@Test
	void testBankGet() throws Exception {
		mockMvc.perform(get("/bank")).andExpect(status().isOk()).andExpect(view().name("form"))
				.andExpect(model().attributeExists("bankCustomer"));
	}

	@ParameterizedTest
	@CsvSource({ "500,balance", "-500,negativeBalance" })
	void testBankPost(int balance, String view) throws Exception {
		var id = "123";
		var expResult = new BankCustomer(id, "test", "test", balance);

		when(bankService.getCustomer(id)).thenReturn(expResult);

		mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer(id)))
				.andExpect(status().isOk()).andExpect(view().name(view))
				.andExpect(model().attributeExists("customer"))
				.andExpect(model().attribute("customer", expResult));
	}

	@Test
	void testBankUnknownPost() throws Exception {
		var id = "123";

		mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer(id)))
				.andExpect(status().isOk()).andExpect(view().name("unknownCustomer"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"abc", "011", "11", "1000"})
	void testBankPost_InvalidId(String id) throws Exception {
		mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer(id)))
				.andExpect(status().isOk()).andExpect(view().name("form"))
				.andExpect(model().attributeDoesNotExist("customer"))
				.andExpect(model().attributeHasFieldErrors("bankCustomer", "id"));
	        
	}
}