package com.example.EWDJ_End;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import domain.event.Event;
import domain.event.EventManager;
import domain.room.Room;
import domain.room.RoomManager;
import domain.user.User;
import domain.user.UserManager;
import domain.user.UserRole;

@Component
public class InitDataConfig implements CommandLineRunner {
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Autowired
	private EventManager eventManager;

	@Autowired
	private RoomManager roomManager;

	@Autowired
	private UserManager userManager;

	@Override
	public void run(String... args) {
		Room room1 = new Room("A123", 10), room2 = new Room("B123", 30);
		roomManager.addRoom(room1);
		roomManager.addRoom(room2);

		Event event1 = new Event(
				"Event 1",
				"Description 1",
				room1,
				LocalDateTime.now(),
				"0097",
				0,
				10.00,
				List.of("Naam 1")
		);

		Event event2 = new Event(
				"Event 2",
				"Description 2",
				room1,
				LocalDateTime.now().minusWeeks(1),
				"0097",
				0,
				10.00,
				List.of("Naam 2")
		);

		Event event3 = new Event(
				"Event 3",
				"This is a very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long description.",
				room2,
				LocalDateTime.now().plusDays(1),
				"0097",
				0,
				10.00,
				List.of("Naam 3", "Naam 4")
		);

		eventManager.addEvent(event1);
		eventManager.addEvent(event2);
		eventManager.addEvent(event3);

		User user = new User("User", encoder.encode("test"), UserRole.USER);
		User admin = new User("Admin", encoder.encode("test"), UserRole.ADMIN);
		
		userManager.addUser(user);
		userManager.addUser(admin);
	}
}
