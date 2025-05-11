package persistence.mapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import domain.room.Room;
import jakarta.persistence.EntityManager;
import persistence.entity.RoomEntity;

public class RoomMapper {
	public static RoomEntity toEntity(Room room, EntityManager em) {
		RoomEntity entity = em.find(RoomEntity.class, room.getName());

		if (entity == null)
			entity = new RoomEntity(room.getName(), room.getCapacity(), new HashSet<>());

		return entity;
	}

	
	public static List<Room> toDomain(Collection<RoomEntity> roomEntities) {
		return roomEntities.stream().map(RoomMapper::toDomain).collect(Collectors.toList());
	}
	public static Room toDomain(RoomEntity roomEntity) {
		return new Room(roomEntity.getName(), roomEntity.getCapacity());
	}
}
