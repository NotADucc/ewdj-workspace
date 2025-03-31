package com.springBoot.jpaMysql_2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.Computer;
import domain.Guest;
import repository.ComputerRepository;
import repository.GuestRepository;

@Component
public class InitDataConfig implements CommandLineRunner {

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private ComputerRepository computerRepository;
	
	@Override
	public void run(String... args) {

		guestRepository.save(new Guest("Keters", "Sandra"));
		guestRepository.save(new Guest("Blondeel", "Tania"));
		guestRepository.save(new Guest("Blondeel", "Jurgen"));
		guestRepository.save(new Guest("Blondeels", "Ann"));
		
		computerRepository.save(new Computer("ABCD", 1234, "Dell"));
		computerRepository.save(new Computer("EFGH", 2345, "HP"));
	}

}
