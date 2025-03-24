package validator;

import static init.InitNumbers.OK_AMOUNT;
import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Numbers;

class NumberValidatorTest {

    private Validator numberValidator;
	
    @BeforeEach
    public void beforeEach() {
    	numberValidator = new NumberValidator();
    }

    private Numbers createNumbers(Integer number1, Integer number2)
    {
    	return Numbers.builder().amount(OK_AMOUNT).number1(number1).number2(number2).build();
    }
    
    private static Stream<Arguments> validNumbersData() {
		return Stream.of(Arguments.of(null, 100), 
				Arguments.of(100, null),
				Arguments.of(null, null), 
				Arguments.of(200, 201),
				Arguments.of(300, 400));
	}
   
    @ParameterizedTest
    @MethodSource("validNumbersData")
    public void testValidNumbers(Integer number1, Integer number2) {
    	Numbers validNumbers = createNumbers(number1, number2);
    	Errors errors = new BeanPropertyBindingResult(validNumbers, "account");
    	numberValidator.validate(validNumbers, errors);
        assertThat(errors.getAllErrors()).isEmpty();
    }
    
    private static Stream<Arguments> invalidNumbersData() {
		return Stream.of(Arguments.of(100, 100), 
				Arguments.of(100, 50),
				Arguments.of(0, -20),
				Arguments.of(20, 0),
				Arguments.of(0, 0),
				Arguments.of(-2, -10));
	}
   
    @ParameterizedTest
    @MethodSource("invalidNumbersData")
    public void testInvalidNumbers(Integer number1, Integer number2) {
    	Numbers validNumbers = createNumbers(number1, number2);
    	Errors errors = new BeanPropertyBindingResult(validNumbers, "account");
    	numberValidator.validate(validNumbers, errors);
        assertThat(errors.getAllErrors()).isNotEmpty();
        assertThat(errors.getErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("number1")).isNotNull();
    }
}
