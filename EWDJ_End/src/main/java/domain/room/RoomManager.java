package domain.room;

import org.springframework.beans.factory.annotation.Autowired;

public class RoomManager {
	
	@Autowired
	private IRoomRepository roomRepository;
	
	public void AddRoom(Room room) {
		if (roomRepository.existsByName(room.getName())) {
			throw new IllegalArgumentException("Room already exists.");
		}
	}
}
