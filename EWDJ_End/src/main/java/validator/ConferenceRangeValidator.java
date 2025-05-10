package validator;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.event.Event;

public class ConferenceRangeValidator implements Validator {
	@Autowired
	private LocalDateTime conferenceRangeStart;
	@Autowired
	private LocalDateTime conferenceRangeEnd;

	@Override
	public boolean supports(Class<?> klass) {
		return Event.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LocalDateTime dateTime = ((Event) target).getDateTime();

		if (dateTime == null)
			return;
		
		if (dateTime.isBefore(conferenceRangeStart) || dateTime.isAfter(conferenceRangeEnd)) {
			errors.rejectValue(
					"dateTime", 
					"conferenceRangeValidator.message", 
					new Object[] { conferenceRangeStart, conferenceRangeEnd },
					"DateTime is not between Conferenceperiod range."
				);
		}
	}
}