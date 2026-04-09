package com.whyweclimb.backend.intercepter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import com.whyweclimb.backend.domain.room.service.MessageService;
import com.whyweclimb.backend.domain.room.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class UserInterceptor implements ChannelInterceptor {

	private final MessageService messageService;
	private final RoomService roomService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor
                = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
        	String roomCode = "";
        	try {
        		roomCode = messageService.getAccess(accessor.getSessionId()).getRoomCode();
        		messageService.decreaseNumberOfPeople(accessor.getSessionId());

        		if (messageService.playerList(roomCode).isEmpty()) {
        			roomService.deleteRoom(roomCode);
        		}
			} catch (NullPointerException e) {
				log.warn("session already cleaned up for sessionId: {}", accessor.getSessionId());
			}
        }
        return message;
    }
}
