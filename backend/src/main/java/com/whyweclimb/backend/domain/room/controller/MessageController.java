package com.whyweclimb.backend.domain.room.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.MessageFindRequest;
import com.whyweclimb.backend.domain.room.service.MessageService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final MessageService messageService;
    
    @MessageMapping("/chat/message")
    public void message(Message message){
    	log.info("[name: "+message.getSender()+", key input: space-"+message.getSpace()+" left-"+message.getLeft()+" right-"+message.getRight()+"]");
    	
    	Message before = messageService.readMessage(MessageFindRequest.builder()
    			.id(message.getId())
    			.sender(message.getSender())
    			.build());

    	messageService.saveMessage(message);
    	
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomCode(), message);
    }
}
