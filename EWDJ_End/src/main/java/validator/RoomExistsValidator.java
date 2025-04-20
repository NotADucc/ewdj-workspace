package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.IHasRoom;
import domain.room.IRoomRepository;
import domain.room.Room;

public class RoomExistsValidator implements Validator {

	private IRoomRepository roomRepository;

	@Override
	public boolean supports(Class<?> klass) {
		return IHasRoom.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Room room = ((IHasRoom) target).getRoom();

		if (room == null)
			return;

		if (roomRepository.exists(room.getName())) {
			errors.rejectValue("room", "", "room does not exist.");
		}
	}
}