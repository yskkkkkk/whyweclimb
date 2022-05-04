package com.whyweclimb.backend.domain.room.model;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RedisHash(value = "message")
public class Message {
    public enum MessageType {
        ENTER, MOVE
    }
    @Id
    @Indexed
    private Integer id;
    private MessageType type;
    private String roomCode;
    @Indexed
    private String sender;
    private Boolean space;
    private Boolean left;
    private Boolean right;
}
