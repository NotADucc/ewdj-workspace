package persistence.mapper;

import java.util.List;
import java.util.stream.Collectors;

import domain.event.Event;
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

	public static List<Event> toDomain(List<EventEntity> eventEntities) {
		return eventEntities.stream().map(EventMapper::toDomain).collect(Collectors.toList());
	}

	public static Event toDomain(EventEntity eventEntity) {
		return new Event(
				eventEntity.getEventId(),
				eventEntity.getName(),
				eventEntity.getDescription(),
				RoomMapper.toDomain(eventEntity.getRoom()),
				eventEntity.getDateTime(),
				eventEntity.getBeamercode(),
				eventEntity.getBeamercheck(),
				eventEntity.getPrice(),
				eventEntity.getSpeakers()
		);
	}
}
