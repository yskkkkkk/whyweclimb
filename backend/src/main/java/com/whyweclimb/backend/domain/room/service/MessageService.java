package com.whyweclimb.backend.domain.room.service;

import com.whyweclimb.backend.entity.Access;

import java.util.List;

public interface MessageService {
	void increaseNumberOfPeople(Access room);
	void decreaseNumberOfPeople(String sessionId);
	boolean roomStatus(String roomCode);
	List<Access> playerList(String roomCode);
	String getReady(Integer userSeq);
	Access getAccess(String sessionId);
}
