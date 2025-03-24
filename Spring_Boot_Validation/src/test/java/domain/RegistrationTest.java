package domain;

import static init.InitRegistration.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class RegistrationTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private static Stream<Arguments> validRegistrationData() {
		return Stream.of(
				Arguments.of("abcd", OK_EMAIL),
				Arguments.of("abcdeabcdeabcde", OK_EMAIL),
				Arguments.of(OK_USERNAME, OK_EMAIL),
				Arguments.of(OK_USERNAME, "a@b")
		);
	}

	private Registration createRegistration(String username, String email) {
		return Registration.builder().userName(username).password(OK_PASSWORD)
				.confirmPassword(OK_CONFIRM_PASSWORD).email(email).build();

	}

	@ParameterizedTest
	@MethodSource("validRegistrationData")
	void testValidRegistration(String username, String email) {
		Registration validRegistration = createRegistration(username, email);

		Set<ConstraintViolation<Registration>> violations = validator.validate(validRegistration);
		assertThat(violations).isEmpty();
	}

	private static Stream<Arguments> invalidRegistrationData() {
		return Stream.of(
				Arguments.of("abc", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "userName"),
				Arguments.of(
						"abcdeabcdeabcdea",
						OK_PASSWORD,
						OK_CONFIRM_PASSWORD,
						OK_EMAIL,
						"userName"
				),
				Arguments.of("ab1c", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "userName"),
				Arguments.of("1abcdefg", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "userName"),
				Arguments.of("", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "userName"),

				Arguments.of(OK_USERNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, "test", "email"),

				Arguments.of(OK_USERNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, "", "email"),

				Arguments.of(OK_USERNAME, "123", "123", OK_EMAIL, "password"),
				Arguments.of(
						OK_USERNAME,
						"123456789012345678901",
						"123456789012345678901",
						OK_EMAIL,
						"password"
				),
				Arguments.of(OK_USERNAME, "", "1234", OK_EMAIL, "password"),
				Arguments.of(OK_USERNAME, "   ", "1234", OK_EMAIL, "password"),

				// @ValidPasswords
				// Arguments.of(OK_USERNAME, OK_PASSWORD, OK_PASSWORD+"1", OK_EMAIL,
				// "password"),

				Arguments.of(OK_USERNAME, "1234", "", OK_EMAIL, "confirmPassword"),
				Arguments.of(OK_USERNAME, "1234", "   ", OK_EMAIL, "confirmPassword")
		);
	}

	@ParameterizedTest
	@MethodSource("invalidRegistrationData")
	void testInvalidRegistration(
			String username,
			String password,
			String confirmPassword,
			String email,
			String expected
	) {
		Registration invalidRegistration = Registration.builder().userName(username)
				.password(password).confirmPassword(confirmPassword).email(email).build();

		Set<ConstraintViolation<Registration>> violations = validator.validate(invalidRegistration);
		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals(expected));
	}

}