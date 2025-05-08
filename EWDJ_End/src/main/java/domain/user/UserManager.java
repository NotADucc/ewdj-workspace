package domain.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserManager {
	IUserRepository userRepository;

	public void addUser(User user) {
		userRepository.addUser(user);
	}
}
