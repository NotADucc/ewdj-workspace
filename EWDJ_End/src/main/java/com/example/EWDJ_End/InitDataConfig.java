package com.example.EWDJ_End;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.event.Event;
import domain.event.EventManager;
import domain.room.Room;
import domain.room.RoomManager;

@Component
public class InitDataConfig implements CommandLineRunner {

	@Autowired
	private EventManager eventManager;

	@Autowired
	private RoomManager roomManager;

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
				LocalDateTime.now().plusDays(1),
				"0097",
				0,
				10.00,
				List.of("Naam 2")
		);
		
		Event event3 = new Event(
				"Event 3",
				"Description 3",
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
	}
}
