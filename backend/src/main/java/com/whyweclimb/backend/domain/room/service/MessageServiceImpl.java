package com.whyweclimb.backend.domain.room.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.MessageFindRequest;
import com.whyweclimb.backend.domain.room.model.Room;
import com.whyweclimb.backend.domain.room.repo.MessageRedisRepository;
import com.whyweclimb.backend.domain.room.repo.RoomRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	private final MessageRedisRepository messageRepository;
	private final RoomRedisRepository roomRepository;
	
	@Override
	public boolean saveMessage(Message message) {
		messageRepository.save(message);
		return true;
	}

	@Override
	public Message readMessage(MessageFindRequest request) {
		Message message = messageRepository.findByIdAndSender(request.getId(), request.getSender());
		System.out.println(message);
		
		return message;
	}

	@Override
	public void increaseNumberOfPeople(String roomCode) {
		Optional<Room> room = roomRepository.findById(roomCode);
		room.ifPresent(selectRoom -> {
			roomRepository.save(Room.builder()
					.roomCode(roomCode)
					.count(selectRoom.getCount()+1)
					.build());
		});
		
		if (!room.isPresent()) {
			roomRepository.save(Room.builder()
					.roomCode(roomCode)
					.count(1)
					.build());
		}
	}

	@Override
	public void decreaseNumberOfPeople(String roomCode) {
		Optional<Room> room = roomRepository.findById(roomCode);
		room.ifPresent(selectRoom -> {
			roomRepository.save(Room.builder()
					.roomCode(roomCode)
					.count(selectRoom.getCount()-1)
					.build());
		});		
	}
}
