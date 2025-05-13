package com.example.EWDJ_End.rest.perform;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.EWDJ_End.rest.API_BASE_PATHS;

import dto.model.RoomOutputDTO;

public class PerformRestRoom {

	private final String BASE_URI = "http://localhost:8080";
	private final String ROOMS_URI = "%s%s".formatted(BASE_URI, API_BASE_PATHS.ROOMS_URI);
	private WebClient webClient = WebClient.create();

	public PerformRestRoom() throws Exception {
		System.out.println("\n------- GET A123 -------");
		getRoom("A123");

		try {
			System.out.println("\n------- GET FAIL A000 ------- ");
			getRoom("A000");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void getARoom(String uri) {
		webClient.get().uri(uri).retrieve().bodyToMono(RoomOutputDTO.class)
				.doOnSuccess(PerformRestRoom::printRoom).block();
	}

	private void getRoom(String name) {
		getARoom("%s/%s".formatted(ROOMS_URI, name));
	}

	private static void printRoom(RoomOutputDTO room) {
		System.out.printf(
				"Name=%s, Capacity=%s",
				room.name(),
				room.capacity()
		);
	}
}
