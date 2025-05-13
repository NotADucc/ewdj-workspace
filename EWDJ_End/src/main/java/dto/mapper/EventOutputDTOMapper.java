package dto.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import com.example.EWDJ_End.rest.API_BASE_PATHS;

import domain.event.Event;
import dto.model.EventOutputDTO;

public class EventOutputDTOMapper {
	public static EventOutputDTO toDTO(Event event) {
		String roomUri = "%s/%s".formatted(API_BASE_PATHS.ROOMS_URI, event.getRoom().getName());
		return new EventOutputDTO(
				event.getId(),
				event.getName(),
				event.getDescription(),
				roomUri,
				event.getDateTime(),
				event.getPrice(),
				event.getSpeakers()
		);
	}
	
	public static Collection<EventOutputDTO> toDTO(Collection<Event> event) {
		return event.stream().map(EventOutputDTOMapper::toDTO).collect(Collectors.toList());
	}
}
