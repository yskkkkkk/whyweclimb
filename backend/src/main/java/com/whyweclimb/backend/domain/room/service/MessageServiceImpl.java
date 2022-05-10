package com.whyweclimb.backend.domain.room.service;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.MessageFindRequest;
import com.whyweclimb.backend.domain.room.model.Access;
import com.whyweclimb.backend.domain.room.repo.MessageRedisRepository;
import com.whyweclimb.backend.domain.room.repo.AccessRedisRepository;
import com.whyweclimb.backend.domain.room.repo.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	private final MessageRedisRepository messageRedisRepository;
	private final AccessRedisRepository accessRedisRepository;
	private final RoomRepository roomRepository;
	
	@Override
	public boolean saveMessage(Message message) {
		messageRedisRepository.save(message);
		return true;
	}

	@Override
	public Message readMessage(MessageFindRequest request) {
		Message message = messageRedisRepository.findByIdAndSender(request.getId(), request.getSender());
		System.out.println(message);
		
		return message;
	}

	@Override
	public void increaseNumberOfPeople(Access room) {
		accessRedisRepository.save(room);
	}

	@Override
	public void decreaseNumberOfPeople(String sessionId) {
		accessRedisRepository.deleteById(sessionId);
	}

	@Override
	public boolean roomStatus(String roomCode) {
		boolean result = false;

		int now = accessRedisRepository.countByRoomCode(roomCode).size();
		int max = roomRepository.findByRoomCode(roomCode).orElse(null).getRoomMaxNum();
		
		if (now < max) result = true;

		return result;
	}
	
	
}
