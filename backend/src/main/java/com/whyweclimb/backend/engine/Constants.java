package com.whyweclimb.backend.engine;

public final class Constants {

    public static final double WIDTH = 1000;
    public static final double HEIGHT = 800;
    public static final double volume = 0.3;

    public static final double speed = 2.7 * 2;
    public static final double gravity = 0.19 * 2 * 2;
    public static final double globalFriction = 0.992;
    public static final double groundFriction = 0.76;
    public static final double sideJump = 5.1 * 2;
    public static final double boundFriction = 0.66;
    public static final double JumpConst = 15.0;
    public static final double chargingConst = 600.0;

}

//public enum Constants2 {
//    WIDTH(1000),
//    HEIGHT(800),
//    VOLUME(0.3),
//    SPEED(2.7 * 2),
//    GRAVITY(0.19 * 2 * 2),
//    GLOBALFRICTION(0.992),
//    GROUNDFRICTION(0.76),
//    SIDEJUMP(5.1 * 2),
//    BOUNDFRICTION(0.66),
//    JUMPCONST(15.0),
//    CHARGINGCONST(600.0);
//
//    private Double constant;
//
//    Constants2(double constant) {
//        this.constant = constant;
//    }
//
//}