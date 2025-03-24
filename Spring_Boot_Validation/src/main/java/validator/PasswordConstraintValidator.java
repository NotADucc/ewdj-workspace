package validator;

import domain.Registration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator
		implements ConstraintValidator<ValidPasswords, Registration> {

	@Override
	public void initialize(ValidPasswords constraintAnnotation) {
	}

	@Override
	public boolean isValid(Registration registration, ConstraintValidatorContext context) {

		if (registration.getPassword() == null || registration.getConfirmPassword() == null) {
			return true;
		}

		boolean isValid = registration.getPassword().equals(registration.getConfirmPassword());

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					context.getDefaultConstraintMessageTemplate()
			).addPropertyNode("password").addConstraintViolation();
		}

		return isValid;
	}
}