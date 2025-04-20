package domain.event;

public interface IEventRepository {
	boolean DoesEventExistOnSpecificDay(Event event);
}
