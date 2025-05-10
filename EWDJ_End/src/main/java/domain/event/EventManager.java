package domain.event;

import java.util.List;

import domain.LocaleException;
import domain.room.IRoomRepository;
import domain.user.IUserRepository;
import domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class EventManager {

	private IEventRepository eventRepository;
	private IRoomRepository roomRepository;
	private IUserRepository userRepository;
	private final static int MAX_FAVORITE_EVENTS = 5;

	public void addEvent(Event event) {
		if (eventRepository.doesEventExistOnSpecificDay(event)) {
			throw new LocaleException(
					"EventManager.addEvent.DoesEventExistOnSpecificDay",
					new Object[] { event.getName(), event.getDateTime() }
			);
		}

		if (roomRepository.isRoomBooked(event.getRoom(), event.getDateTime())) {
			throw new LocaleException(
					"EventManager.addEvent.isRoomBooked",
					new Object[] { event.getRoom().getName(), event.getDateTime() }
			);
		}

		eventRepository.addEvent(event);
	}

	public void editEvent(int eventId, Event event) {
		if (!eventRepository.doesEventExist(eventId)) {
			throw new LocaleException(
					"EventManager.editEvent.doesEventExist",
					new Object[] { eventId }
			);
		}

		if (eventId != event.getId()) {
			throw new LocaleException(
					"EventManager.editEvent.idDoesNotMatch",
					new Object[] { eventId, event.getId() }
			);
		}

		var eventsOnSameDay = eventRepository
				.getAllEventsOnSpecificDay(event.getDateTime().toLocalDate());

		var eventWithSameNameExists = eventsOnSameDay.stream()
				.filter(e -> e.getId() != eventId && e.getName().equals(event.getName()))
				.count() > 0;

		if (eventWithSameNameExists) {
			throw new LocaleException(
					"EventManager.editEvent.DoesEventExistOnSpecificDay",
					new Object[] { event.getName(), event.getDateTime() }
			);
		}

		var db_event = eventRepository.getEvent(eventId);
		var hasRoomOrDateTimeChanged = !db_event.getRoom().equals(event.getRoom())
				|| !db_event.getDateTime().equals(event.getDateTime());

		if (hasRoomOrDateTimeChanged
				&& roomRepository.isRoomBooked(event.getRoom(), event.getDateTime())) {
			throw new LocaleException(
					"EventManager.editEvent.isRoomBooked",
					new Object[] { event.getRoom().getName(), event.getDateTime() }
			);
		}

		eventRepository.editEvent(event);
	}

	public List<Event> giveEventsSorted() {
		return eventRepository.getAllEventsSorted();
	}

	public List<Event> getFavoriteEventsForUser(String username) {
		return eventRepository.getFavoriteEventsForUser(username);
	}

	public Event giveEvent(int id) {
		if (!eventRepository.doesEventExist(id)) {
			throw new LocaleException("EventManager.giveEvent.doesEventExist", new Object[] { id });
		}

		return eventRepository.getEvent(id);
	}

	public void toggleFavorite(int eventId, String username) {
		if (!eventRepository.doesEventExist(eventId)) {
			throw new LocaleException(
					"EventManager.toggleFavorite.doesEventExist",
					new Object[] { eventId }
			);
		}

		if (!userRepository.doesUserExist(username)) {
			throw new LocaleException(
					"EventManager.toggleFavorite.doesUserExist",
					new Object[] { username }
			);
		}

		User user = userRepository.getUserByUsername(username);
		Event event = eventRepository.getEvent(eventId);

		int favoriteCount = user.getFavoriteEvents().size();
		var wantsToFavorite = user.getFavoriteEvents().stream().filter(x -> x.getId() == eventId)
				.count() == 0;

		if (wantsToFavorite && favoriteCount >= MAX_FAVORITE_EVENTS) {
			throw new LocaleException(
					"EventManager.toggleFavorite.favoriteCountExceeded",
					new Object[] { MAX_FAVORITE_EVENTS }
			);
		}

		if (wantsToFavorite) {
			user.addEvent(event);
			eventRepository.favorite(eventId, user.getId());
		} else {
			user.removeEvent(event);
			eventRepository.unfavorite(eventId, user.getId());
		}
	}

	public boolean isFavorited(int eventId, String username) {
		if (!eventRepository.doesEventExist(eventId)) {
			throw new LocaleException(
					"EventManager.isFavorited.doesEventExist",
					new Object[] { eventId }
			);
		}

		if (!userRepository.doesUserExist(username)) {
			throw new LocaleException(
					"EventManager.isFavorited.doesUserExist",
					new Object[] { username }
			);
		}

		User user = userRepository.getUserByUsername(username);
		return user.getFavoriteEvents().stream().filter(x -> x.getId() == eventId).count() > 0;
	}

	public boolean hasMaxFavoriteCountBeenExceeded(String username) {
		if (!userRepository.doesUserExist(username)) {
			throw new LocaleException(
					"EventManager.hasMaxFavoriteCountBeenExceeded.doesUserExist",
					new Object[] { username }
			);
		}

		User user = userRepository.getUserByUsername(username);
		return user.getFavoriteEvents().size() >= MAX_FAVORITE_EVENTS;
	}
}
