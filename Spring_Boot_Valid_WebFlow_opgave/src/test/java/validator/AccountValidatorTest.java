package validator;

import static init.InitAccount.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Account;

class AccountValidatorTest {

private Validator accountValidator;
	
    /*@BeforeEach
    public void beforeEach() {
    	accountValidator = new AccountValidator();
    }*/
    
    //percent is een double in klasse Account
    //---------------------------------------
    /*@ParameterizedTest
    @ValueSource(doubles = {0.0, 0.2, 0.4})
    public void testValidPercent(double percent) {
    	Account validAccount = Account.builder().balance(OK_BALANCE).percent(percent).email(OK_EMAIL).build();
    	Errors errors = new BeanPropertyBindingResult(validAccount, "account");
    	accountValidator.validate(validAccount, errors);
        assertThat(errors.getAllErrors()).isEmpty();
    }*/
    
    //percent is een Double in klasse Account
    //---------------------------------------
    /*@ParameterizedTest
    @NullSource //percent is Double, not double
    @ValueSource(doubles = {0.0, 0.2, 0.4})
    public void testValidPercent(Double percent) {
    	Account validAccount = Account.builder().balance(OK_BALANCE).percent(percent).email(OK_EMAIL).build();
    	Errors errors = new BeanPropertyBindingResult(validAccount, "account");
    	accountValidator.validate(validAccount, errors);
        assertThat(errors.getAllErrors()).isEmpty();
    }*/
    
    /*
    @ParameterizedTest
    @ValueSource(doubles = {0.25, 0.01, 0.05, 0.59 })
    public void testInvalidPercent(double percent) {
    	Account invalidAccount = Account.builder().balance(OK_BALANCE).percent(percent).email(OK_EMAIL).build();
    	Errors errors = new BeanPropertyBindingResult(invalidAccount, "account");
    	accountValidator.validate(invalidAccount, errors);
        assertThat(errors.getAllErrors()).isNotEmpty();
        assertThat(errors.getErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("percent")).isNotNull();
    }*/
}
