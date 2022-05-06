package com.whyweclimb.backend.engine;

import lombok.Getter;

@Getter
public enum Constants {
    WIDTH(1000),
    HEIGHT(800),
    VOLUME(0.3),
    SPEED(2.7 * 2),
    GRAVITY(0.19 * 2 * 2),
    GLOBALFRICTION(0.992),
    GROUNDFRICTION(0.76),
    SIDEJUMP(5.1 * 2),
    BOUNDFRICTION(0.66),
    JUMPCONST(15.0),
    CHARGINGCONST(600.0);

    private Double constant;

    Constants(double constant) {
        this.constant = constant;
    }

}