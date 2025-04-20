package persistence.mapper;

import domain.event.Event;
import domain.room.Room;
import jakarta.persistence.EntityManager;
import persistence.entity.EventEntity;
import persistence.entity.RoomEntity;

public class EventMapper {
	public static EventEntity toEntity(Event event, EntityManager em) {
		EventEntity entity = em.find(EventEntity.class, event.getId());

		if (entity == null) {
			RoomEntity roomEntity = RoomMapper.toEntity(event.getRoom(), em);
			entity = new EventEntity(
					event.getName(),
					event.getDescription(),
					roomEntity,
					event.getDateTime(),
					event.getBeamercode(),
					event.getBeamercheck(),
					event.getPrice(),
					event.getSpeakers()
			);
			roomEntity.AddEvent(entity);
		}

		return entity;
	}
}
