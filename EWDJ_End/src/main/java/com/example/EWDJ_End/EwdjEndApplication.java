package com.example.EWDJ_End;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import domain.room.IRoomRepository;
import persistence.repository.RoomRepository;

@SpringBootApplication
public class EwdjEndApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EwdjEndApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/overview");
	}

	@Bean
	IRoomRepository roomRepository() {
		return new RoomRepository();
	}

	@Bean
	LocalDateTime conferentieperiodeStart() {
		return LocalDateTime.now();
	}

	@Bean
	LocalDateTime conferentieperiodeEnd() {
		return LocalDateTime.now().plusYears(1);
	}
}
