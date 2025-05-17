package validator;

import java.util.List;

import domain.IHasSpeakers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmptySpeakerValidator implements ConstraintValidator<HasEmptySpeakers, IHasSpeakers> {
	@Override
	public void initialize(HasEmptySpeakers constraintAnnotation) {
	}

	@Override
	public boolean isValid(IHasSpeakers target, ConstraintValidatorContext context) {
		List<String> speakers = target.getSpeakers();

		if (speakers == null)
			return true;

		boolean isValid = speakers.stream().filter(String::isBlank).count() == 0;

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					context.getDefaultConstraintMessageTemplate()
			).addPropertyNode("speakers").addConstraintViolation();
		}

		return isValid;
	}
}