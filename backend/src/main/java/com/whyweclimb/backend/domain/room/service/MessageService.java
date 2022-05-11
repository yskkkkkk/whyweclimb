package com.whyweclimb.backend.domain.room.service;

import com.whyweclimb.backend.domain.play.model.PlayerResponse;
import com.whyweclimb.backend.domain.room.dto.MessageFindRequest;
import com.whyweclimb.backend.entity.Access;
import com.whyweclimb.backend.entity.Message;
import com.whyweclimb.backend.entity.Status;

import java.util.List;

public interface MessageService {

	public boolean saveStatus(PlayerResponse status);
	public PlayerResponse readStatus(Integer userSeq);
	
	boolean saveMessage(Message message);
	Message readMessage(MessageFindRequest request);
	
	void increaseNumberOfPeople(Access room);
	void decreaseNumberOfPeople(String sessionId);
	
	boolean roomStatus(String roomCode);
	
	List<Access> playerList(String roomCode); 
}
