package com.whyweclimb.backend.domain.play.model.inner;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CollideBox {
    boolean collide;
    boolean lb;
    boolean rb;
    boolean lt;
    boolean rt;
}
