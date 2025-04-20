package domain.event;

public interface IEventRepository {
	boolean DoesEventExistOnSpecificDay(Event event);
	void AddEvent(Event event);
}
