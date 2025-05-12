package exceptions;

public class DuplicateEmployeeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateEmployeeException(Integer id) {
	    super("Duplicate employee %s".formatted(id));
	}
}

