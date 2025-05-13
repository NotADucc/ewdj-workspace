package com.example.EWDJ_End.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import domain.event.EventManager;
import domain.event.IEventRepository;
import domain.room.IRoomRepository;
import domain.room.RoomManager;
import domain.user.IUserRepository;
import domain.user.UserManager;
import persistence.repository.EventRepository;
import persistence.repository.RoomRepository;
import persistence.repository.UserRepository;

@Configuration
public class DomainConfig {
	@Bean
	IEventRepository eventRepository() {
		return new EventRepository();
	}

	@Bean
	IRoomRepository roomRepository() {
		return new RoomRepository();
	}

	@Bean @Primary
	IUserRepository userRepository() {
		return new UserRepository();
	}

	@Bean
	EventManager eventManager(IEventRepository eventRepository, IRoomRepository roomRepository, IUserRepository userRepository) {
		return new EventManager(eventRepository, roomRepository, userRepository);
	}

	@Bean
	RoomManager roomManager(IRoomRepository roomRepository) {
		return new RoomManager(roomRepository);
	}

	@Bean
	UserManager userManager(IUserRepository userRepository) {
		return new UserManager(userRepository);
	}
}
