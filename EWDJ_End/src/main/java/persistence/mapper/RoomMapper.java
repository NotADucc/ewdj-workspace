package persistence.mapper;

import domain.room.Room;
import jakarta.persistence.EntityManager;
import persistence.entity.RoomEntity;

public class RoomMapper {
	public static RoomEntity toEntity(Room room, EntityManager em) {
		RoomEntity entity = em.find(RoomEntity.class, room.getName());
		
		if (entity == null)
			entity = new RoomEntity(room.getName(), room.getCapacity());
		
		return entity;
	}
}
