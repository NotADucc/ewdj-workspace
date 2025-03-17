package domain;

import static init.InitAccount.*;
import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

class AccountTest {

	private Validator validator;
	    
	@BeforeEach
	public void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private static Stream<Arguments> validAccountData() {
		return Stream.of(
				Arguments.of(new BigDecimal("10000"), OK_PERCENT, OK_EMAIL),
				Arguments.of(OK_BALANCE, OK_PERCENT, OK_EMAIL),	
				Arguments.of(OK_BALANCE, 0.59, OK_EMAIL),
				Arguments.of(OK_BALANCE, 0.0, OK_EMAIL),
				Arguments.of(OK_BALANCE, OK_PERCENT, "a@b")
		);
	}
	
	@ParameterizedTest
	@MethodSource("validAccountData")
	public void testValidAccount(BigDecimal balance, double percent, String email) {
		Account validAccount = Account.builder().balance(balance).
				percent(percent).email(email).build();

		Set<ConstraintViolation<Account>> violations = validator.validate(validAccount);
		assertThat(violations).isEmpty();
		//OR assertTrue(violations.isEmpty());
	}

	private static Stream<Arguments> invalidAccountData() {
		return Stream.of(
				Arguments.of(new BigDecimal("8500"), OK_PERCENT, OK_EMAIL, "balance"),
				Arguments.of(new BigDecimal("9999.99"), OK_PERCENT, OK_EMAIL, "balance"),
				Arguments.of(null, OK_PERCENT, OK_EMAIL, "balance"),
				Arguments.of(OK_BALANCE, 0.65, OK_EMAIL, "percent"),
				Arguments.of(OK_BALANCE, 0.60, OK_EMAIL, "percent"),
				Arguments.of(OK_BALANCE, -0.01, OK_EMAIL, "percent"),
				Arguments.of(OK_BALANCE, OK_PERCENT, "", "email"),
				Arguments.of(OK_BALANCE, OK_PERCENT, null, "email"),
				Arguments.of(OK_BALANCE, OK_PERCENT, "test.com", "email")
		);
	}
	
	@ParameterizedTest
	@MethodSource("invalidAccountData")
	public void testInvalidAccountBalance(BigDecimal balance, double percent, String email, String expected) 
	{
		Account invalidAccount = Account.builder().balance(balance).
				percent(percent).email(email).build();

		Set<ConstraintViolation<Account>> violations = validator.validate(invalidAccount);
		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals(expected));
		//OR assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(expected)));
	}
		
}
