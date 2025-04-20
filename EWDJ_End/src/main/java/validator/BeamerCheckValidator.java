package validator;

import domain.event.Event;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BeamerCheckValidator implements ConstraintValidator<BeamerChecksum, Event> {
	private int divisor;

	@Override
	public void initialize(BeamerChecksum constraintAnnotation) {
		divisor = constraintAnnotation.divisor();
	}

	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		String code = event.getBeamercode();
		Integer check = event.getBeamercheck();

		if (code == null || check == null)
			return true;

		// can add a try catch
		var parsedCode = Integer.parseInt(code);
		boolean isValid = parsedCode % divisor == check;

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					context.getDefaultConstraintMessageTemplate()
			).addPropertyNode("beamercheck").addConstraintViolation();
		}

		return isValid;
	}
}