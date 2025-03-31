package validator;

import domain.Price;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PercentValidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return Price.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		var price = (Price) target;
		
		if (price.getPercentDecrease() == null || price.getPercentIncrease() == null)
			return;

		if (price.getPercentIncrease() < price.getPercentDecrease()) {
			errors.rejectValue(
					"percentIncrease",
					"price.percentIncrease.lowerThanDecrease",
					"increase >= decrease."
			);
		}
	}
}