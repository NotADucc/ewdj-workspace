package domain;

import static init.InitNumbers.*;
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

class NumbersTest {

	private Validator validator;
    
	@BeforeEach
	public void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private static Stream<Arguments> validNumbersData() {
		return Stream.of(
				
				Arguments.of(5000.49, OK_NUMBER1, OK_NUMBER2),
				Arguments.of(1000.0, OK_NUMBER1, OK_NUMBER2),
				Arguments.of(0.0, OK_NUMBER1, OK_NUMBER2),
				Arguments.of(-100.25, OK_NUMBER1, OK_NUMBER2),
				
				Arguments.of(OK_AMOUNT, 1, 1001),	
				Arguments.of(OK_AMOUNT, 11000, 10000),	
				Arguments.of(OK_AMOUNT, OK_NUMBER1, OK_NUMBER2),
				
				Arguments.of(OK_AMOUNT, OK_NUMBER1, 1500),
				Arguments.of(OK_AMOUNT, OK_NUMBER1, -500)
		);
	}
	
	@ParameterizedTest
	@MethodSource("validNumbersData")
	public void testValidNumbers(Double amount, Integer number1, Integer number2) {
		Numbers validNumbers = Numbers.builder().amount(amount).
				number1(number1).number2(number2).build();

		Set<ConstraintViolation<Numbers>> violations = validator.validate(validNumbers);
		assertThat(violations).isEmpty();
	}

	private static Stream<Arguments> invalidNumbersData() {
		return Stream.of(
				
				Arguments.of(5000.50, OK_NUMBER1, OK_NUMBER2, "amount"),
				Arguments.of(85000.05, OK_NUMBER1, OK_NUMBER2, "amount"),
				
				Arguments.of(OK_AMOUNT, null, OK_NUMBER2, "number1"),
				Arguments.of(OK_AMOUNT, 0, 100, "number1"),	
				Arguments.of(OK_AMOUNT, 11001, 10500, "number1"),	
				
				Arguments.of(OK_AMOUNT, OK_NUMBER1, OK_NUMBER1+1001, "number1"),
				Arguments.of(OK_AMOUNT, OK_NUMBER1, OK_NUMBER1-1001, "number1"),
				
				Arguments.of(OK_AMOUNT, OK_NUMBER1, null, "number2")
		);
	}
	
	@ParameterizedTest
	@MethodSource("invalidNumbersData")
	public void testInvalidAccountBalance(Double amount, Integer number1, Integer number2, String expected) 
	{
		Numbers invalidNumbers = Numbers.builder().amount(amount).
				number1(number1).number2(number2).build();

		Set<ConstraintViolation<Numbers>> violations = validator.validate(invalidNumbers);
		
		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals(expected));
	}

}
