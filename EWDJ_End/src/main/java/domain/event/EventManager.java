package domain.event;

import domain.LocaleException;
import domain.room.IRoomRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventManager {

	private IEventRepository eventRepository;
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
