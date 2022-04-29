package com.whyweclimb.backend.engine;

import com.whyweclimb.backend.engine.inner.CollideBox;

public class AABB {
    double x;
    double y;
    double X;
    double Y;
    double width;
    double height;

    public AABB(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.X = x + w;
        this.Y = Y + h;
        this.width = w;
        this.height = h;
    }

    public boolean checkCollidePoint(double px, double py) {
        return px > this.x && px < this.X && py > this.y && py < this.Y;
    }

    public CollideBox checkCollideBox(AABB aabb) {
        boolean rlb = this.checkCollidePoint(aabb.x, aabb.y);
        boolean rrb = this.checkCollidePoint(aabb.X, aabb.y);
        boolean rlt = this.checkCollidePoint(aabb.x, aabb.Y);
        boolean rrt = this.checkCollidePoint(aabb.X, aabb.Y);

        return new CollideBox(rlb || rrb || rlt || rrt, rlb, rrb, rlt, rrt);
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        this.X += dx;
        this.Y += dy;
    }
}
