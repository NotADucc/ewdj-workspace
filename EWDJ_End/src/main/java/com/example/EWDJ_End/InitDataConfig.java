package com.example.EWDJ_End;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
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
		roomManager.AddRoom(room1);
		roomManager.AddRoom(room2);
	}
}
