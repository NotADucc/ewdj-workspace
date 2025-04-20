package com.example.EWDJ_End;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import domain.room.IRoomRepository;
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
		registry.addRedirectViewController("/", "/welcome");
	}

	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}

	@Bean
	LocalDateTime conferenceRangeStart() {
		return LocalDateTime.now();
	}

	@Bean
	LocalDateTime conferenceRangeEnd() {
		return LocalDateTime.now().plusYears(1);
	}
}
