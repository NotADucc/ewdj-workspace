package com.springBoot.EWDJ_End;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.room.Room;
import domain.room.RoomManager;

@Component
public class StringToRoomConverter implements Converter<String, Room> {

	@Autowired
    private RoomManager roomManager;

    @Override
    public Room convert(String source) {
    	return roomManager.giveRoom(source);
    }
}
