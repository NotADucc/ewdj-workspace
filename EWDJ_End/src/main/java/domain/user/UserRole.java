package domain.user;

public enum UserRole {
	USER,
	BANNED,
	ADMIN;
	public String resourceBundleCode() {
		return switch (this) {
			case USER -> "userRole.user";
			case BANNED -> "userRole.banned";
			case ADMIN -> "userRole.admin";
			default -> throw new IllegalArgumentException();
		};
	}
}
