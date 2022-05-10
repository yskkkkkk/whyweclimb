package com.whyweclimb.backend.domain.room.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerResponse {
    private Integer userSeq;
    private String userId;
    private boolean direction_L;
    private boolean crouching;
    private boolean running_R;
    private boolean running_L;
    private boolean onGround;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double size;
    private double radius;
    private double jumpGauge;
    private double runningTime;
}
