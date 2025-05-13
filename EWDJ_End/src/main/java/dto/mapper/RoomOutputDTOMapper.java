package dto.mapper;

import domain.room.Room;
import dto.model.RoomOutputDTO;

public class RoomOutputDTOMapper {
	public static RoomOutputDTO toDTO(Room room) {
		return new RoomOutputDTO(room.getName(), room.getCapacity());
	}
}
