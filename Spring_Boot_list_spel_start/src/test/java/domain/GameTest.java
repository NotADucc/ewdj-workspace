package domain;

import static domain.InitGame.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class GameTest {

	private Validator validator;
    
	@BeforeEach
	public void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	/*
	@ParameterizedTest
	@ValueSource(ints = { TODO })
	public void testValidGame(Integer number) {
		Game validGame = TODO

		Set<ConstraintViolation<Game>> violations = validator.validate(validGame);
		assertThat(violations).isEmpty();
	}
	
	@ParameterizedTest
	@NullSource
	@ValueSource(ints = { TODO,  -10, 0, 100 })
	public void testInvalidGame(Integer number) 
	{
		Game invalidGame = TODO

		Set<ConstraintViolation<Game>> violations = validator.validate(invalidGame);
		assertThat(violations).isNotEmpty();
		assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("number"));
	}*/

}