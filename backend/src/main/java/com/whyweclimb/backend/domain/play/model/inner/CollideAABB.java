package com.whyweclimb.backend.domain.play.model.inner;

import com.whyweclimb.backend.domain.play.model.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CollideAABB {
    Vector collide;
    boolean endPoint;
}
