package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import domain.GreetingService;
import domain.GreetingServiceImpl;


@Configuration
public class BeanConfiguration {

    @Bean
    GreetingService greetingService() {
    	var service = new GreetingServiceImpl();
    	service.setGreeting("Snello World");
        return service;
    }
}