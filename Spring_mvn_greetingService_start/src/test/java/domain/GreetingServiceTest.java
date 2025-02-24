package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import configuration.BeanConfiguration;

@SpringJUnitWebConfig(BeanConfiguration.class)
class GreetingServiceTest {

	@Autowired
	private GreetingService service;

	private final String MESSAGE = "Snello World";

	@Test
	void test() {
		assertEquals(MESSAGE, service.sayGreeting());
	}
}