package persistence.repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.event.Event;
import domain.event.IEventRepository;
import persistence.entity.EventEntity;
import persistence.entity.UserEntity;
import persistence.mapper.EventMapper;
import persistence.mapper.RoomMapper;

@Repository
public class EventRepository extends GenericRepository<EventEntity> implements IEventRepository {

	public EventRepository() {
		super(EventEntity.class);
	}

	@Override
	public boolean doesEventExistOnSpecificDay(Event event) {
		String jpql = """
				SELECT e
				FROM EventEntity e
				WHERE e.name = :name AND CAST(e.dateTime AS DATE) = :date
				""";

		var res = em.createQuery(jpql, EventEntity.class).setParameter("name", event.getName())
				.setParameter("date", event.getDateTime().toLocalDate()).getResultList();

		return res.size() > 0;
	}

	@Override
	@Transactional
	public void addEvent(Event event) {
		EventEntity eventEntity = EventMapper.toEntity(event, em);
		insert(eventEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> getAllEvents() {
		return EventMapper.toDomain(findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> getAllEventsOnSpecificDate(LocalDate date) {
		String jpql = """
				SELECT e
				FROM EventEntity e
				WHERE CAST(e.dateTime AS DATE) = :date
				""";

		var res = em.createQuery(jpql, EventEntity.class).setParameter("date", date)
				.getResultList();

		return EventMapper.toDomain(res);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> getAllEventsSorted() {
		String jpql = """
				SELECT e
				FROM EventEntity e
				ORDER BY e.dateTime
				""";

		var res = em.createQuery(jpql, EventEntity.class).getResultList();

		return EventMapper.toDomain(res);
	}

	@Override
	public boolean doesEventExist(int id) {
		return exists(id);
	}

	@Override
	public Event getEvent(int id) {
		return EventMapper.toDomain(get(id));
	}

	@Override
	@Transactional
	public void favorite(int eventid, int userid) {
		var user = em.find(UserEntity.class, userid);
		var event = get(eventid);
		user.addEvent(event);
	}

	@Override
	@Transactional
	public void unfavorite(int eventid, int userid) {
		var user = em.find(UserEntity.class, userid);
		var event = get(eventid);
		user.removeEvent(event);
	}

	@Override
	@Transactional
	public void editEvent(Event event) {
		var db_event = get(event.getId());
		db_event.setName(event.getName());
		db_event.setDescription(event.getDescription());
		db_event.setRoom(RoomMapper.toEntity(event.getRoom(), em));
		db_event.setDateTime(event.getDateTime());
		db_event.setBeamercode(event.getBeamercode());
		db_event.setBeamercheck(event.getBeamercheck());
		db_event.setPrice(event.getPrice());
		db_event.setSpeakers(event.getSpeakers());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> getFavoriteEventsForUser(String username) {
		String jpql = """
				SELECT e
				FROM UserEntity e
				WHERE e.name = :name
				""";

		var res = em.createQuery(jpql, UserEntity.class).setParameter("name", username)
				.getResultList();

		var user = res.getFirst();

		return EventMapper.toDomain(
				user.getFavoriteEvents().stream()
						.sorted(
								Comparator.comparing(EventEntity::getDateTime)
										.thenComparing(EventEntity::getName)
						).collect(Collectors.toList())
		);
	}
}
