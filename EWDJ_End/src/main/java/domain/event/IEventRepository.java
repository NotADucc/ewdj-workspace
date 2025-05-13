package domain.event;

import java.time.LocalDate;
import java.util.List;

public interface IEventRepository {
	boolean doesEventExistOnSpecificDay(Event event);
	void addEvent(Event event);
	void editEvent(Event event);
	List<Event> getAllEvents();
	List<Event> getAllEventsOnSpecificDate(LocalDate date);
	List<Event> getAllEventsSorted();
	List<Event> getFavoriteEventsForUser(String username);
	boolean doesEventExist(int id);
	Event getEvent(int id);
	void favorite(int eventid, int userid);
	void unfavorite(int eventid, int userid);
}
