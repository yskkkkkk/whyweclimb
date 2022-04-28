package com.whyweclimb.backend.engine;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Block {
    int level;
    AABB aabb;

    public AABB convert(){
        return new AABB(this.aabb.x, this.aabb.y + this.level * Constants.HEIGHT, this.aabb.width, this.aabb.height);
    }
}
