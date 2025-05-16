package init;

import domain.user.User;
import domain.user.UserRole;

public interface InitUser {
	final int OK_ID = 1;
	final String OK_NAME = "user";
	
	final User OK_USER = new User(
			OK_ID,
			OK_NAME,
			"Test12345",
			UserRole.USER,
			null
	);
	
	final User OK_ADMIN = new User(
			OK_ID + 1,
			OK_NAME,
			"Test12345",
			UserRole.ADMIN,
			null
	);
}
