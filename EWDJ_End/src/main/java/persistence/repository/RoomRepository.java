package persistence.repository;

import java.time.LocalDateTime;

import domain.room.IRoomRepository;
import domain.room.Room;
import persistence.entity.RoomEntity;

public class RoomRepository extends GenericRepository<Room> implements IRoomRepository {

	public RoomRepository() {
		super(Room.class);
	}

	@Override
	public boolean IsRoomBooked(Room room, LocalDateTime dateTime) {
		var res = em.createNamedQuery("RoomEntity.isRoomBooked", RoomEntity.class)
				.setParameter("name", room.getName())
				.setParameter("dateTime", dateTime)
				.getResultList();
		
		return res.size() > 0;
	}
}
