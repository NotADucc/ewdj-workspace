package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class BankCustomerTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@ParameterizedTest
	@ValueSource(strings = { "100", "111", "123", "999" })
	void testValidCustomer(String id) {
		BankCustomer bankCustomer = new BankCustomer(id);

		Set<ConstraintViolation<BankCustomer>> violations = validator.validate(bankCustomer);
		assertThat(violations).isEmpty();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "   ", "abc", "1", "12", "023", "099", "1000", "1234" })
	void testInvalidCustomer(String id) {
		BankCustomer bankCustomer = new BankCustomer(id);

		Set<ConstraintViolation<BankCustomer>> violations = validator.validate(bankCustomer);
		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("id"));
	}

}