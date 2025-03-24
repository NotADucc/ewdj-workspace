package exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
public class ReservationNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Getter
	private final String courtName;
	@Getter
	private final LocalDate date;
	@Getter
	private final int hour;

}