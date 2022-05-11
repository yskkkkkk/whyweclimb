package com.whyweclimb.backend.domain.play.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerResponse {
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
    // collide 상태 추가 wall left right top wall
    // jump 상태
}
