package com.springBoot.validation;

import static init.InitRegistration.*;
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

import domain.Registration;

@WebMvcTest(RegistrationController.class)
@Import(SpringBootValidationApplication.class)
class RegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetRequest() throws Exception {

		mockMvc.perform(get("/registration")).andExpect(view().name("registrationForm"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("registration"));
	}

	private Registration createRegistration(
			String username,
			String password,
			String confirmPassword,
			String email
	) {
		return Registration.builder().userName(username).password(password)
				.confirmPassword(confirmPassword).email(email).build();
	}

	@Test
	void testPostRequestValidRegistration() throws Exception {

		Registration validRegistration = createRegistration(
				OK_USERNAME,
				OK_PASSWORD,
				OK_CONFIRM_PASSWORD,
				OK_EMAIL
		);

		mockMvc.perform(post("/registration").flashAttr("registration", validRegistration))
				.andExpect(status().isOk()).andExpect(view().name("registrationSuccess"))
				.andExpect(model().attributeExists("registration"));
	}

	private static Stream<Arguments> invalidRegistrationData() {
		return Stream.of(
				Arguments.of(
						"ab",
						OK_PASSWORD,
						OK_CONFIRM_PASSWORD,
						OK_EMAIL,
						new String[] { "userName" }
				),
				Arguments.of(
						OK_USERNAME,
						OK_PASSWORD,
						"12345678",
						OK_EMAIL,
						new String[] { "password" }
				),
				Arguments.of(
						OK_USERNAME,
						OK_PASSWORD,
						"",
						OK_EMAIL,
						new String[] { "confirmPassword" }
				),
				Arguments.of(
						OK_USERNAME,
						OK_PASSWORD,
						OK_CONFIRM_PASSWORD,
						"test",
						new String[] { "email" }
				),

				Arguments.of(
						OK_USERNAME,
						"1234",
						"123566",
						"test",
						new String[] { "password", "email" }
				),
				Arguments.of(
						"12345",
						"",
						" ",
						"test",
						new String[] { "userName", "password", "confirmPassword", "email" }
				)
		);
	}

	@ParameterizedTest
	@MethodSource("invalidRegistrationData")
	void testPostRequestInvalidRegistration(
			String username,
			String password,
			String conformPassword,
			String email,
			String[] expectedErrors
	) throws Exception {

		Registration invalidRegistration = createRegistration(
				username,
				password,
				conformPassword,
				email
		);

		mockMvc.perform(post("/registration").flashAttr("registration", invalidRegistration))
				.andExpect(status().isOk()).andExpect(view().name("registrationForm"))
				.andExpect(model().attributeHasFieldErrors("registration", expectedErrors));
	}

}
