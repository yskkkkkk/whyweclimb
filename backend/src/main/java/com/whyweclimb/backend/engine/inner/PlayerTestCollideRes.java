package com.whyweclimb.backend.engine.inner;

import com.whyweclimb.backend.engine.Vector;
import lombok.Getter;

@Getter
public class PlayerTestCollideRes {
    // 초기화??
    String side = "";
    double set = 0;
    Vector vSet = null;
    Vector ref = null;

    public PlayerTestCollideRes(String side, double set){
        this.side = side;
        this.set = set;
    }

    public PlayerTestCollideRes(String side, Vector vSet, Vector ref){
        this.side = side;
        this.vSet = vSet;
        this.ref = ref;
    }
}
