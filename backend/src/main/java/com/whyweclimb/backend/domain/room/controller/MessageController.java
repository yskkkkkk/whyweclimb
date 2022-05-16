package com.whyweclimb.backend.domain.room.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.whyweclimb.backend.domain.room.dto.AccessResponse;
import com.whyweclimb.backend.domain.room.service.MessageService;
import com.whyweclimb.backend.entity.Access;
import com.whyweclimb.backend.entity.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

	private final SimpMessageSendingOperations messagingTemplate;
	private final MessageService messageService;

	@MessageMapping("/play/message")
	public void play(Message message) {
//		log.info("[name: " + message.getSender() + ", key input: space-" + message.getSpace() + " left-" + message.getLeft()
//				+ " right-" + message.getRight() + "]");

		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomCode(), message);
	}

	@MessageMapping("/room/entrance")
	public void comeInPlayer(Access access) {
		if (messageService.roomStatus(access.getRoomCode())) {
			log.info("[user come: created session - " + access.getSessionId() + "]");
			messageService.increaseNumberOfPeople(access);

			AccessResponse response = new AccessResponse(messageService.playerList(access.getRoomCode()), "OK");
			messagingTemplate.convertAndSend("/sub/chat/room/" + access.getRoomCode(), response);
		} else {
			Map<String, String> result = new HashMap<String, String>();
			result.put("message", "full");
			messagingTemplate.convertAndSend("/sub/chat/room/" + access.getRoomCode(), result);
		}
	}

	@MessageMapping("/room/ready")
	public void playerReady(Integer userSeq) {
		String roomCode = messageService.getReady(userSeq);
		AccessResponse response = new AccessResponse(messageService.playerList(roomCode));

		messagingTemplate.convertAndSend("/sub/chat/room/" + roomCode, response);
	}
}
