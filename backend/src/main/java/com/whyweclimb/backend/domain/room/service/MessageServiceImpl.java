package com.whyweclimb.backend.domain.room.service;

import org.springframework.stereotype.Service;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.MessageFindRequest;
import com.whyweclimb.backend.domain.room.repo.MessageRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
	private final MessageRedisRepository messageRepository;
	
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
}
