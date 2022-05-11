package com.whyweclimb.backend.domain.room.service;

import com.whyweclimb.backend.domain.room.model.MessageFindRequest;
import com.whyweclimb.backend.entity.Access;
import com.whyweclimb.backend.entity.Message;

import java.util.List;

public interface MessageService {
	// message save
	boolean saveMessage(Message message);
	// message read
	Message readMessage(MessageFindRequest request);
	
	void increaseNumberOfPeople(Access room);
	
	void decreaseNumberOfPeople(String sessionId);
	
	boolean roomStatus(String roomCode);
	
	List<Access> playerList(String roomCode); 
}
