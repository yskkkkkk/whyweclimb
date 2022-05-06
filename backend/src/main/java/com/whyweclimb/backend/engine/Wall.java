package com.whyweclimb.backend.engine;

import com.whyweclimb.backend.engine.inner.CollideAABB;

public class Wall {
    int level;
    double x0;
    double y0;
    double x1;
    double y1;
    double wx;
    double wy;

    public Wall(int level, double x0, double y0, double wx, double wy) {
        this.level = level;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x0 + wx;
        this.y1 = y0 + wy;
        this.wx = wx;
        this.wy = wy;
    }

    public CollideAABB checkCollideAABB(AABB aabb, double vx, double vy) {
        Vector collide = this.checkCollide(aabb.x, aabb.y, aabb.x + vx, aabb.y + vy)
                ? new Vector(aabb.x, aabb.y)
                : this.checkCollide(aabb.X, aabb.y, aabb.X + vx, aabb.y + vy)
                        ? new Vector(aabb.X, aabb.y)
                        : this.checkCollide(aabb.x, aabb.Y, aabb.x + vx, aabb.Y + vy)
                                ? new Vector(aabb.x, aabb.Y)
                                : this.checkCollide(aabb.X, aabb.Y, aabb.X + vx, aabb.Y + vy)
                                        ? new Vector(aabb.X, aabb.Y)
                                        : null;

        if (collide != null)
            return new CollideAABB(collide, false);
        else {
            collide = aabb.checkCollidePoint(this.x0, this.y0)
                    ? new Vector(this.x0, this.y0)
                    : aabb.checkCollidePoint(this.x1, this.y1)
                            ? new Vector(this.x1, this.y1)
                            : null;

            return new CollideAABB(collide, collide != null);
        }
    }

    public boolean checkCollide(double ax, double ay, double bx, double by) {
        double z0 = (this.x1 - this.x0) * (ay - this.y0) - (this.y1 - this.y0) * (ax - this.x0);
        double z1 = (this.x1 - this.x0) * (by - this.y1) - (this.y1 - this.y0) * (bx - this.x1);

        double z2 = (bx - ax) * (this.y0 - ay) - (by - ay) * (this.x0 - ax);
        double z3 = (bx - ax) * (this.y1 - by) - (by - ay) * (this.x1 - bx);

        return z0 * z1 < 0 && z2 * z3 < 0;
    }

    public Vector getNormal() {
        Vector res = new Vector(this.y1 - this.y0, this.x0 - this.x1);
        res.normalize();

        return res;
    }

    public Wall convert() {
        return new Wall(this.level, this.x0, this.y0 + this.level * Constants.HEIGHT.getConstant(), this.wx, this.wy);
    }
}
