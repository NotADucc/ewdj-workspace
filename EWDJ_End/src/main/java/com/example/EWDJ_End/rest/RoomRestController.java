package com.example.EWDJ_End.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.room.RoomManager;
import dto.mapper.RoomOutputDTOMapper;
import dto.model.RoomOutputDTO;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {
	@Autowired
	private RoomManager roomManager;
	
    @GetMapping("/{name}") 
    public RoomOutputDTO getEmployee(@PathVariable String name) {
    	return RoomOutputDTOMapper.toDTO(roomManager.giveRoom(name));
    }
}
