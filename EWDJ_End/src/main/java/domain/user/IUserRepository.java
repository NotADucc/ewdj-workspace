package domain.user;

public interface IUserRepository {
	void addUser(User user);
	User getUserByUsername(String username);
	boolean doesUserExist(String username);
}
