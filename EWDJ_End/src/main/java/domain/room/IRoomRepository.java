package domain.room;

import java.time.LocalDateTime;

import domain.IGenericRepository;

public interface IRoomRepository extends IGenericRepository<Room> {
	boolean IsRoomBooked(Room room, LocalDateTime dateTime);
}
