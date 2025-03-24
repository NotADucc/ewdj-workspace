package validator;

import static init.InitRegistration.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Registration;

class RegistrationValidatorTest {

	private Validator registrationValidator;

	@BeforeEach
	void beforeEach() {
		registrationValidator = new RegistrationValidator();
	}

	private Registration createRegistration(String confirmPassword) {
		return Registration.builder().userName(OK_USERNAME).password(OK_PASSWORD)
				.confirmPassword(confirmPassword).email(OK_EMAIL).build();
	}

	@Test
	void testValidPasswords() {
		Registration validRegistration = createRegistration(OK_CONFIRM_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(validRegistration, "registration");
		registrationValidator.validate(validRegistration, errors);
		assertThat(errors.getAllErrors()).isEmpty();
	}

	@Test
	void testInvalidPasswords() {
		Registration invalidRegistration = createRegistration(OK_PASSWORD + "1");
		Errors errors = new BeanPropertyBindingResult(invalidRegistration, "registration");
		registrationValidator.validate(invalidRegistration, errors);
		assertThat(errors.getAllErrors()).isNotEmpty();
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("password")).isNotNull();
	}
}
