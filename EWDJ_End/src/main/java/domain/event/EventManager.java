package domain.event;

import org.springframework.beans.factory.annotation.Autowired;

import domain.LocaleException;
import domain.room.IRoomRepository;

public class EventManager {

	@Autowired
	private IEventRepository eventRepository;

	@Autowired
	private IRoomRepository roomRepository;

	public void AddEvent(Event event) {
		if (eventRepository.DoesEventExistOnSpecificDay(event)) {
			throw new LocaleException(
					"EventManager.AddEvent.DoesEventExistOnSpecificDay",
					new Object[] { event.getName(), event.getDateTime() }
			);
		}

		if (roomRepository.isRoomBooked(event.getRoom(), event.getDateTime())) {
			throw new LocaleException(
					"EventManager.AddEvent.isRoomBooked",
					new Object[] { event.getRoom().getName(), event.getDateTime() }
			);
		}
		
		eventRepository.AddEvent(event);
	}
}
