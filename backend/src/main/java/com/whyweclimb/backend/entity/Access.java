package com.whyweclimb.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@RedisHash(value = "access")
public class Access {
	@Id
	@Indexed
	private String sessionId;
    @Indexed
    private String roomCode;
    @Indexed
    private Integer userSeq;
    private String userId;
	private Boolean ready;

	public void setReady(Boolean ready) {
		this.ready = ready;
	}
}
