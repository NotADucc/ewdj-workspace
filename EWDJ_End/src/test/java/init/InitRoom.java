package init;

import domain.room.Room;

public interface InitRoom {
	final String OK_NAME = "A123";
	final int OK_CAPACITY = 1;
	
	final Room OK_ROOM = new Room(
			OK_NAME,
			OK_CAPACITY
	);
}
