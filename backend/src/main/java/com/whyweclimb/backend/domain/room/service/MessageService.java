package com.whyweclimb.backend.domain.room.service;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.MessageFindRequest;
import com.whyweclimb.backend.domain.room.model.Room;

public interface MessageService {
	// message save
	boolean saveMessage(Message message);
	// message read
	Message readMessage(MessageFindRequest request);
	
	void increaseNumberOfPeople(Room room);
	
	void decreaseNumberOfPeople(String sessionId);
	
	boolean roomStatus(String roomCode);
}
