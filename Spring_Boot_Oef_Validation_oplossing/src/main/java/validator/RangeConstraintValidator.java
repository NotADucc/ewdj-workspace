package validator;

import domain.Numbers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RangeConstraintValidator implements ConstraintValidator<RangeNumbers, Numbers> {
	private int range;

	@Override
	public void initialize(RangeNumbers constraintAnnotation) {
		range = constraintAnnotation.range();
	}

	@Override
	public boolean isValid(Numbers numbers, ConstraintValidatorContext context) {

		if (numbers.getNumber1() == null || numbers.getNumber2() == null)
			return true;

		boolean isValid = Math.abs(numbers.getNumber1() - numbers.getNumber2()) <= range;
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("number1").addConstraintViolation();
		}

		return isValid;
	}
}