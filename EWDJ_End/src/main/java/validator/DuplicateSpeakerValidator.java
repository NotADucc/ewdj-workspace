package validator;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import domain.IHasSpeakers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DuplicateSpeakerValidator
		implements ConstraintValidator<HasDuplicateSpeakers, IHasSpeakers> {
	@Override
	public void initialize(HasDuplicateSpeakers constraintAnnotation) {
	}

	@Override
	public boolean isValid(IHasSpeakers target, ConstraintValidatorContext context) {
		List<String> speakers = target.getSpeakers();

		if (speakers == null)
			return true;
			
		speakers = speakers.stream().map(String::toLowerCase)
				.collect(Collectors.toList());
		
		boolean isValid = speakers.size() == new HashSet<>(speakers).size();

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					context.getDefaultConstraintMessageTemplate()
			).addPropertyNode("speakers").addConstraintViolation();
		}

		return isValid;
	}
}