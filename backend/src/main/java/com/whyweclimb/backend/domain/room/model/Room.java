package com.whyweclimb.backend.domain.room.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@RedisHash(value = "room")
public class Room {
	@Id
	@Indexed
	private String sessionId;
    @Indexed
    private String roomCode;
}
