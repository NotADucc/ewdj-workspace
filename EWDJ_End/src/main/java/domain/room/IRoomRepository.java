package domain.room;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomRepository {
	boolean isRoomBooked(Room room, LocalDateTime dateTime);
	boolean existsByName(String name);
	void addRoom(Room room);
	List<Room> giveRooms();
	Room giveRoom(String name);
}
