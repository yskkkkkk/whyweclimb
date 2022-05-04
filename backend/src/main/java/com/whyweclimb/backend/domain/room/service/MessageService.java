package com.whyweclimb.backend.domain.room.service;

import com.whyweclimb.backend.domain.room.model.Message;

public interface MessageService {
	// message save
	boolean saveMessage(Message message);
	// message read
	Message readMessage();
}
