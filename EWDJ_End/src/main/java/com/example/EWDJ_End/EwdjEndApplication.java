package com.example.EWDJ_End;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import domain.event.EventManager;
import domain.event.IEventRepository;
import domain.room.IRoomRepository;
import domain.room.RoomManager;
import persistence.repository.EventRepository;
import persistence.repository.RoomRepository;

@SpringBootApplication
@EnableJpaRepositories("persistence.repository")
@EntityScan("persistence.entity")
public class EwdjEndApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EwdjEndApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/events");
	}

	@Bean
	SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

		Properties mappings = new Properties();
		mappings.put("domain.LocaleException", "error/locale-exception");

		r.setExceptionMappings(mappings);
		return r;
	}

	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}

	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	LocalDateTime conferenceRangeStart() {
		return LocalDateTime.of(LocalDate.now().getYear(), 1, 1, 1, 0);
	}

	@Bean
	LocalDateTime conferenceRangeEnd() {
		return conferenceRangeStart().plusYears(1);
	}

	@Bean
	IEventRepository eventRepository() {
		return new EventRepository();
	}

	@Bean
	IRoomRepository roomRepository() {
		return new RoomRepository();
	}

	@Bean
	EventManager eventManager(IEventRepository eventRepository, IRoomRepository roomRepository) {
		return new EventManager(eventRepository, roomRepository);
	}

	@Bean
	RoomManager roomManager(IRoomRepository roomRepository) {
		return new RoomManager(roomRepository);
	}
}
