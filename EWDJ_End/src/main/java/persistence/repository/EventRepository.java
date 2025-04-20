package persistence.repository;

import domain.event.Event;
import domain.event.IEventRepository;
import persistence.entity.EventEntity;

public class EventRepository extends GenericRepository<Event> implements IEventRepository {

	public EventRepository() {
		super(Event.class);
	}

	@Override
	public boolean DoesEventExistOnSpecificDay(Event event) {
		var res = em.createNamedQuery("EventEntity.doesEventExistOnSpecificDay", EventEntity.class)
				.setParameter("name", event.getName())
				.setParameter("date", event.getDateTime().toLocalDate())
				.getResultList();

		return res.size() > 0;
	}
}
