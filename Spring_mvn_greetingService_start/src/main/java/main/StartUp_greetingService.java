package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configuration.BeanConfiguration;
import domain.GreetingService;

public class StartUp_greetingService {

	public static void main(String[] args) {

		try (var ctx = new AnnotationConfigApplicationContext(BeanConfiguration.class)) {
			var service = ctx.getBean("greetingService", GreetingService.class);
			System.out.println(service.sayGreeting());
		}
	}
}