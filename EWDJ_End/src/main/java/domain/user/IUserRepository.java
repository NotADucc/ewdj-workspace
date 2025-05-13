package domain.user;

import java.util.List;

import domain.event.Event;

public interface IUserRepository {
	void addUser(User user);
	User getUserByUsername(String username);
	boolean doesUserExist(String username);
	List<Event> getFavoriteEvents(String username);
}
