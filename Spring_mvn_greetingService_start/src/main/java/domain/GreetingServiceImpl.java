package domain;

import lombok.Setter;

@Setter
public class GreetingServiceImpl implements GreetingService {
	
	private String greeting;

	@Override
	public String sayGreeting() {
		return greeting;
	}

}
