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


		int parsedCode = 0;
		boolean isValid = false;
		
		try {
			parsedCode = Integer.parseInt(code);
			isValid = parsedCode % divisor == check;
		} catch (Exception e) {
			
		}

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					context.getDefaultConstraintMessageTemplate()
			).addPropertyNode("beamercheck").addConstraintViolation();
		}

		return isValid;
	}
}