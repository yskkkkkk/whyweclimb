package com.whyweclimb.backend.engine.inner;

import com.whyweclimb.backend.engine.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CollideAABB {
    Vector collide;
    boolean endPoint;
}
