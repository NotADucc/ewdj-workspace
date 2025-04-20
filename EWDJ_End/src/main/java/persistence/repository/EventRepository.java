package persistence.repository;

import org.springframework.stereotype.Repository;

import domain.event.Event;
import domain.event.IEventRepository;
import persistence.entity.EventEntity;

@Repository
public class EventRepository extends GenericRepository<EventEntity> implements IEventRepository {

	public EventRepository() {
		super(EventEntity.class);
	}

	@Override
	public boolean DoesEventExistOnSpecificDay(Event event) {
		String jpql = """
				SELECT e
				FROM EventEntity e
				WHERE e.name = :name AND CAST(e.dateTime AS DATE) = :date
				""";
		
		var res = em.createQuery(jpql, EventEntity.class)
				.setParameter("name", event.getName())
				.setParameter("date", event.getDateTime().toLocalDate())
				.getResultList();

		return res.size() > 0;
	}
}
