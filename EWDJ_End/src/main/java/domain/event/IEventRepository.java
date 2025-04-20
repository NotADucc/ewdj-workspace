package domain.event;

import domain.IGenericRepository;

public interface IEventRepository extends IGenericRepository<Event> {
	boolean DoesEventExistOnSpecificDay(Event event);
}
