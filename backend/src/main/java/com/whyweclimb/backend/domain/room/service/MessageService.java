package com.whyweclimb.backend.domain.room.service;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.MessageFindRequest;

public interface MessageService {
	// message save
	boolean saveMessage(Message message);
	// message read
	Message readMessage(MessageFindRequest request);
	
	void increaseNumberOfPeople(String roomCode);
	
	void decreaseNumberOfPeople(String roomCode);
}
