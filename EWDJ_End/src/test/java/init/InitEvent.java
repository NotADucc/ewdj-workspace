package init;

import java.time.LocalDateTime;
import java.util.List;

import domain.event.Event;
import domain.room.Room;

public interface InitEvent {
	final int OK_ID = 1234;
	final String OK_NAME = "Test";
	final String OK_DESCRIPTION = "Description";
	final Room OK_ROOM = new Room(InitRoom.OK_NAME, InitRoom.OK_CAPACITY);
	final LocalDateTime OK_TIME = LocalDateTime.now();
	final String OK_B_CODE = "0097";
	final int OK_B_CHECK = 0;
	final Double OK_PRICE = 10.0;
	final List<String> OK_SPEAKERS = List.of("Jeff");
	
	final Event OK_EVENT = new Event(
			OK_ID,
			OK_NAME,
			OK_DESCRIPTION,
			OK_ROOM,
			OK_TIME,
			OK_B_CODE,
			OK_B_CHECK,
			OK_PRICE,
			OK_SPEAKERS
	);
}
