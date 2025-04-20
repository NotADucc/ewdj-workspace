package domain.user;

import java.util.HashSet;
import java.util.Set;

import domain.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {
	private String name;
	private UserRole role;
	private Set<Event> favoriteEvents = new HashSet<>();
	
	public User(String name, UserRole role) {
		this.name = name;
		this.role = role;
	}
}
