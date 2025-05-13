package com.example.EWDJ_End.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.room.RoomManager;
import dto.mapper.RoomOutputDTOMapper;
import dto.model.RoomOutputDTO;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(API_BASE_PATHS.ROOMS_URI)
public class RoomRestController {
	private final RoomManager roomManager;
	
    @GetMapping("/{name}") 
    public RoomOutputDTO getRoom(@PathVariable String name) {
    	return RoomOutputDTOMapper.toDTO(roomManager.giveRoom(name));
    }
}
