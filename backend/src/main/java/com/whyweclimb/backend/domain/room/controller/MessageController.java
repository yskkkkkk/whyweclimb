package com.whyweclimb.backend.domain.room.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.whyweclimb.backend.domain.room.model.Message;
import com.whyweclimb.backend.domain.room.model.MessageFindRequest;
import com.whyweclimb.backend.domain.room.model.Access;
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
    			.build());	// before (이전상태 레디스에서 불러오기)
    	
    	// 여기에서 before (이전상태) + message (입력받은 커맨드로 현재상태 계산)
    	
    	messageService.saveMessage(message);// saveMessage -> saveStatus (현재 상태 저장) 
    	
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomCode(), message); // 상태 반환 
    }
    
    @MessageMapping("/room/entrance")
    @SendToUser
    public void checkPeopleNumber(Access access) {
    	if (messageService.roomStatus(access.getRoomCode())) {
    		messageService.increaseNumberOfPeople(access);
    		log.info("[user come: "+access.getSessionId());
    		
    		// 현재 상태 뱉어줘야함 
    		Map<String, String> result = new HashMap<String, String>();
    		result.put("message", "OK");
    		
    		messagingTemplate.convertAndSend("/sub/chat/room/" + access.getRoomCode(), result);
    	}else {
    		Map<String, String> result = new HashMap<String, String>();
    		result.put("message", "full");
    		messagingTemplate.convertAndSend("/sub/chat/room/" + access.getRoomCode(), result);
    	}
    }
}
