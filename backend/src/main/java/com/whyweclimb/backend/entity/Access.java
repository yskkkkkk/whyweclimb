package com.whyweclimb.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@RedisHash(value = "access")
public class Access {
	@Id
	@Indexed
	private String sessionId;
    @Indexed
    private String roomCode;
    private Integer userSeq;
    private String userId;
    private Boolean ready;
}
