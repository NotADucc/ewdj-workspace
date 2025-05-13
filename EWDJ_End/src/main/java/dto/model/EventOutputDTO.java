package dto.model;

import java.time.LocalDateTime;
import java.util.Collection;

public record EventOutputDTO(int id, String name, String description, String roomURI,
		LocalDateTime dateTime, Double price, Collection<String> speakers) {
}
