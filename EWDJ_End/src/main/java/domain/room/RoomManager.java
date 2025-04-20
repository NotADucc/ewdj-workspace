package domain.room;

import org.springframework.beans.factory.annotation.Autowired;

import domain.LocaleException;

public class RoomManager {
	
	@Autowired
	private IRoomRepository roomRepository;
	
	public void AddRoom(Room room) {
		if (roomRepository.existsByName(room.getName())) {
			throw new LocaleException("RoomManager.AddRoom.existsByName");
		}
		
		roomRepository.AddRoom(room);
	}
}
