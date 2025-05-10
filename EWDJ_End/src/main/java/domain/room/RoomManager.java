package domain.room;

import java.util.List;

import domain.LocaleException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomManager {
	private IRoomRepository roomRepository;

	public List<Room> giveRooms() {
		return roomRepository.giveRooms();
	}

	public Room giveRoom(String name) {
		if (!roomRepository.existsByName(name)) {
			throw new LocaleException("RoomManager.giveRoom.existsByName", new Object[] { name });
		}
		return roomRepository.giveRoom(name);
	}

	public void addRoom(Room room) {
		if (roomRepository.existsByName(room.getName())) {
			throw new LocaleException(
					"RoomManager.AddRoom.existsByName",
					new Object[] { room.getName() }
			);
		}

		roomRepository.AddRoom(room);
	}
}
