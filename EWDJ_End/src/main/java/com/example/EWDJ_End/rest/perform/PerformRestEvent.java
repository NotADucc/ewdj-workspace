package com.example.EWDJ_End.rest.perform;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.EWDJ_End.rest.API_BASE_PATHS;

import dto.model.EventOutputDTO;
import reactor.core.publisher.Mono;

public class PerformRestEvent {

	private final String BASE_URI = "http://localhost:8080";
	private final String EVENTS_URI = "%s%s".formatted(BASE_URI, API_BASE_PATHS.EVENTS_URI);
	private WebClient webClient = WebClient.create();

	public PerformRestEvent() throws Exception {
		var today = LocalDate.now();
		int day = today.getDayOfMonth(), month = today.getMonthValue(), year = today.getYear();
		System.out.println("\n------- GET ALL %s-%s-%s -------".formatted(month, day + 1, year));
		getEventsOnDate("%s-%s-%s".formatted(month, day + 1, year));
	}

	private void getEvents(String uri) {
		webClient.get().uri(uri).retrieve().bodyToFlux(EventOutputDTO.class).flatMap(emp -> {
			printEvent(emp);
			return Mono.empty();
		}).blockLast();
	}

	private void getEventsOnDate(String date) {
		getEvents("%s?date=%s".formatted(EVENTS_URI, date));
	}

	private static void printEvent(EventOutputDTO event) {
		System.out.printf(
				"Name=%s, Description=%s, roomUri=%s, datetime=%s, price=%s, speakers=%s",
				event.id(),
				event.description(),
				event.roomURI(),
				event.dateTime(),
				event.price(),
				event.speakers().stream().collect(Collectors.joining(";"))
		);
	}
}
