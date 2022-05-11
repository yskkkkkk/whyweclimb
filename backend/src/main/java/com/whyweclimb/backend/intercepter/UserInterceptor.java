package com.whyweclimb.backend.intercepter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;

import com.whyweclimb.backend.domain.room.service.MessageService;
import com.whyweclimb.backend.entity.Access;

import lombok.RequiredArgsConstructor;
 
@RequiredArgsConstructor
public class UserInterceptor implements ChannelInterceptor {

	private final MessageService messageService;
	
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor
                = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

//        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
//        	String[] tokens = accessor.getDestination().split("/");
//        	String roomCode = tokens[tokens.length-1]; //Parsed RoomID
//        	
//        	if (messageService.roomStatus(roomCode)) {
//        		messageService.increaseNumberOfPeople(Room.builder()
//        				.roomCode(roomCode)
//        				.sessionId(accessor.getSessionId())
//        				.build());
//        	}else {
//        		return null;
//        	}
//        	
////            StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
////            headerAccessor.setSessionId(accessor.getSessionId());
////            headerAccessor.setSubscriptionId(accessor.getSubscriptionId());
////            if (true) {
////                headerAccessor.setMessage("FULL");
////                customChannel.send(MessageBuilder.createMessage(new byte[0], headerAccessor.getMessageHeaders()));
////                return null;
////            }else{
////                headerAccessor.setMessage("OK");
////            }
////            customChannel.send(MessageBuilder.createMessage(new byte[0], headerAccessor.getMessageHeaders()));
//        }else 
        if(StompCommand.DISCONNECT.equals(accessor.getCommand())) {
        	messageService.decreaseNumberOfPeople(accessor.getSessionId());        	
        }
        return message;
    }
}