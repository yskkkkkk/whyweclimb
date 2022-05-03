package com.whyweclimb.backend.domain.room.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.whyweclimb.backend.domain.room.model.ChatMessage;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message){
//        if(ChatMessage.MessageType.ENTER.equals(message.getType())){
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        }
    	log.info("[key input space-"+message.getSpace()+" left-"+message.getLeft()+" right-"+message.getRight());
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomCode(), message);
    }
}
