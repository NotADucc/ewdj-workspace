package domain.event;

import java.util.List;

import domain.LocaleException;
import domain.room.IRoomRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventManager {

	private IEventRepository eventRepository;
	private IRoomRepository roomRepository;

	public void addEvent(Event event) {
		if (eventRepository.doesEventExistOnSpecificDay(event)) {
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
		
		eventRepository.addEvent(event);
	}
	
	public List<Event> giveEvents() {
		return eventRepository.getAllEvents();
	}
}
