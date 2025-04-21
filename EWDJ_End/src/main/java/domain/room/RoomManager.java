package domain.room;

import domain.LocaleException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomManager {
	private IRoomRepository roomRepository;

	public void addRoom(Room room) {
		if (roomRepository.existsByName(room.getName())) {
			throw new LocaleException("RoomManager.AddRoom.existsByName");
		}

		roomRepository.AddRoom(room);
	}
}
