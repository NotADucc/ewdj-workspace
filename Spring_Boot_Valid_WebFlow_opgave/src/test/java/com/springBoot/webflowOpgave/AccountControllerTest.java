package com.springBoot.webflowOpgave;

import static init.InitAccount.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import domain.Account;

@WebMvcTest(AccountController.class)
@Import(SpringBootValidWebFlowOpgaveApplication.class)
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetRequest() throws Exception {

		mockMvc.perform(get("/account"))
		.andExpect(view().name("accountForm"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("account"));
	}
	
	private Account createAccount(BigDecimal balance, double percent, String email)
	{
		 return Account.builder().balance(balance).percent(percent).email(email).build();
	}
	
	@Test
    public void testPostRequestValidAccount() throws Exception {
		
		Account validAccount = createAccount(OK_BALANCE, OK_PERCENT, OK_EMAIL);
		
        mockMvc.perform(post("/account").flashAttr("account", validAccount))
                .andExpect(status().isOk())
                 .andExpect(view().name("exampleView"))
                .andExpect(model().attributeExists("account"));
        
       
    }

	 private static Stream<Arguments> invalidAccountData() {
	        return Stream.of(
	            Arguments.of(new BigDecimal("8500"), OK_PERCENT, OK_EMAIL, new String[]{"balance"}), // Invalid balance
	            Arguments.of(OK_BALANCE, "0.65", OK_EMAIL, new String[]{"percent"}), // Invalid percent
	            Arguments.of(OK_BALANCE, "0.25", OK_EMAIL, new String[]{"percent"}), // Invalid percent
	            Arguments.of(new BigDecimal("10000"), OK_PERCENT, "invalid-email", new String[]{"email"}), // Invalid email
	            Arguments.of(new BigDecimal("8500"), OK_PERCENT, "invalid-email", new String[]{"balance","email"}), // Invalid balance and email
	            Arguments.of(new BigDecimal("9500"), "0.65", "invalid-email", new String[]{"balance","percent","email"}) // Multiple invalid fields
	        );
	 }
	 
	 @ParameterizedTest
	 @MethodSource("invalidAccountData")
	 public void testPostRequestInvalidAccount(BigDecimal balance, double percent, String email, String[] expectedErrors) throws Exception 
	 {
	   
		 Account invalidAccount = createAccount(balance, percent, email);
			
        mockMvc.perform(post("/account").flashAttr("account", invalidAccount))
                .andExpect(status().isOk())
                .andExpect(view().name("accountForm"))
                .andExpect(model().attributeHasFieldErrors("account", expectedErrors));
    }

}
