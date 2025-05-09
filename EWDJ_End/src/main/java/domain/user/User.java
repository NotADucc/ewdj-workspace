package domain.user;

import java.util.HashSet;
import java.util.Set;

import domain.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
	private int id;
	private String name;
	private String password;
	private UserRole role;
	private Set<Event> favoriteEvents = new HashSet<>();

	public User(String name, String password, UserRole role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	public void addEvent(Event event) {
		int count = favoriteEvents.size();
		favoriteEvents.add(event);
		if (count == favoriteEvents.size()) {
			// can throw exc here
		}
	}
	
	public void removeEvent(Event event) {
		int count = favoriteEvents.size();
		favoriteEvents.remove(event);
		if (count == favoriteEvents.size()) {
			// can throw exc here
		}
	}
}
