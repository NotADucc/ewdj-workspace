package domain.event;

import org.springframework.beans.factory.annotation.Autowired;

public class EventManager {

	@Autowired
	private IEventRepository eventRepository;
	
	public void AddEvent() {
		
	}
}
