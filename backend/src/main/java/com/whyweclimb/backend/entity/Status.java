package com.whyweclimb.backend.entity;

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
@RedisHash(value = "status")
public class Status {
	@Id
	@Indexed
	private Integer userSeq;
	private Boolean direction_L;
	private Boolean crouching;
	private Boolean running_R;
	private Boolean running_L;
	private Boolean onGround;
	private Double x;
	private Double y;
	private Double vx;
	private Double vy;
	private Double size;
	private Double radius;
	private Double jumpGauge;
	private Double runningTime;
	private Integer level;
	private Integer levelMax;
	private Boolean isLanding;
	private Boolean isCollide;
	private Boolean isJump;
}
