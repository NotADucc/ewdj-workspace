package com.example.EWDJ_End.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConferenceConfig {
	@Bean
	LocalDateTime conferenceRangeStart() {
		return LocalDateTime.of(LocalDate.now().getYear(), 1, 1, 1, 0);
	}

	@Bean
	LocalDateTime conferenceRangeEnd(LocalDateTime conferenceRangeStart) {
		return conferenceRangeStart.plusYears(1);
	}
}
