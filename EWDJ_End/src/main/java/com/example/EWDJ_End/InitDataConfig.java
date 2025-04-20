package com.example.EWDJ_End;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import domain.event.EventManager;

@Component
public class InitDataConfig implements CommandLineRunner {

	@Autowired
	private EventManager eventManager;

	@Override
	public void run(String... args) {

	}
}
