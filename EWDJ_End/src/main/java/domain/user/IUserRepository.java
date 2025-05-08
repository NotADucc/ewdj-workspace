package domain.user;

public interface IUserRepository {
	void addUser(User user);
	User getUserByUsername(String username);
}
