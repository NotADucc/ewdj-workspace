package domain.room;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomRepository {
	boolean isRoomBooked(Room room, LocalDateTime dateTime);
	boolean existsByName(String name);
	void AddRoom(Room room);
	List<Room> giveRooms();
}
