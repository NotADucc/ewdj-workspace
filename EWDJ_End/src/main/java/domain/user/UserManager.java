package domain.user;

import java.util.List;

import domain.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserManager {
	IUserRepository userRepository;

	public void addUser(User user) {
		userRepository.addUser(user);
	}
	
	public List<Event> getFavoriteEvents(String username) {
		return userRepository.getFavoriteEvents(username);
	}
}
