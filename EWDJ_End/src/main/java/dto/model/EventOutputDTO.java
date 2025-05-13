package dto.model;

import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.LocalDateTimeDeserializer;
import utils.LocalDateTimeSerializer;

public record EventOutputDTO(int id, String name, String description, String roomURI,
		@JsonSerialize(using = LocalDateTimeSerializer.class) 
		@JsonDeserialize(using = LocalDateTimeDeserializer.class) 
	LocalDateTime dateTime, Double price, Collection<String> speakers) {
}
