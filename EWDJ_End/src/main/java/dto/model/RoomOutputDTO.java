package dto.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "capacity" })
public record RoomOutputDTO(String name, int capacity) {

}
