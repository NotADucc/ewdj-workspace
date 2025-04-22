package domain.event;

import java.util.List;

public interface IEventRepository {
	boolean doesEventExistOnSpecificDay(Event event);
	void addEvent(Event event);
	List<Event> getAllEvents();
	boolean doesEventExist(int id);
	Event getEvent(int id);
}
