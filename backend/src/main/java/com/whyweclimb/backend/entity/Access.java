package com.whyweclimb.backend.entity;

import lombok.Setter;
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
public class Access implements Comparable<Access>{
	@Id
	@Indexed
	private String sessionId;
    @Indexed
    private String roomCode;
    @Indexed
    private Integer userSeq;
    private String userId;
	@Setter
    private Boolean ready;
	private Integer skinSeq;
	@Setter
    private Integer order;

    @Override
	public int compareTo(Access access) {
		return this.order - access.order;
	}
}
