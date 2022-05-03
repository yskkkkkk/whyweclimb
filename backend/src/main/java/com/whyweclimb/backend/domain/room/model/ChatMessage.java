package com.whyweclimb.backend.domain.room.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType {
        ENTER, MOVE
    }

    private MessageType type;
    private String roomCode;
    private String sender;
    private Boolean space;
    private Boolean left;
    private Boolean right;
}
