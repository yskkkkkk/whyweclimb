package com.whyweclimb.backend.domain.play.model;

import com.whyweclimb.backend.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
	int userSeq;
    boolean direction_L;
    boolean crouching;
    boolean running_R;
    boolean running_L;
    boolean onGround;
    double x;
    double y;
    double vx;
    double vy;
    double size;
    double radius;
    double jumpGauge;
    double runningTime;
    int level;
    int levelMax;
    /// audio boolean
    boolean isLanding;
    boolean isCollide;
    boolean isJump;
    
    public PlayerResponse(Status status) {
    	this.userSeq = status.getUserSeq();
    	this.direction_L = status.getDirection_L();
    	this.crouching = status.getCrouching();
    	this.running_R = status.getRunning_R();
    	this.running_L = status.getRunning_L();
    	this.onGround = status.getOnGround();
    	this.x = status.getX();
    	this.y = status.getY();
    	this.vx = status.getVx();
    	this.vy = status.getVy();
    	this.size = status.getSize();
    	this.radius = status.getRadius();
    	this.jumpGauge = status.getJumpGauge();
    	this.runningTime = status.getRunningTime();
    	this.level = status.getLevel();
    	this.levelMax = status.getLevelMax();
    	this.isLanding = status.getIsLanding();
    	this.isCollide = status.getIsCollide();
    	this.isJump = status.getIsJump();
    }
}
