package domain.user;

import java.util.HashSet;
import java.util.Set;

import domain.event.Event;
import lombok.Getter;

@Getter
public class User {
	private int id;
	private String name;
	private String password;
	private UserRole role;
	private Set<Event> favoriteEvents = new HashSet<>();

	public User(int id, String name, String password, UserRole role, Set<Event> favoriteEvents) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
		this.favoriteEvents = favoriteEvents == null ? new HashSet<>() : favoriteEvents;
	}
	
	public User(String name, String password, UserRole role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	public void addEvent(Event event) {
		if (favoriteEvents.contains(event)) {
			// can throw exc here
		}
		favoriteEvents.add(event);
	}
	
	public void removeEvent(Event event) {
		if (!favoriteEvents.contains(event)) {
			// can throw exc here
		}
		favoriteEvents.remove(event);
	}
}
