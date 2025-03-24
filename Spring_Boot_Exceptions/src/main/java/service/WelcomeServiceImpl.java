package service;

import java.time.LocalDate;

import exception.CustomGenericException;
import exception.ReservationNotAvailableException;

public class WelcomeServiceImpl implements WelcomeService {

	@Override
	public void example() {

		 //throw new ReservationNotAvailableException("iTalent", LocalDate.now(), 12);
		//throw new NumberFormatException();
		// throw new CustomGenericException("E888", "This is custom message");
		// throw new IllegalArgumentException();
	}

}
