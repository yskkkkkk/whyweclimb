package com.whyweclimb.backend.engine;

import com.whyweclimb.backend.engine.inner.CollideAABB;
import com.whyweclimb.backend.engine.inner.CollideBox;
import com.whyweclimb.backend.engine.inner.PlayerTestCollideRes;

public class Player {
    boolean direction_L;
    boolean crouching;
    boolean running_R;
    boolean running_L;
    boolean onGround;
    double x;
    double y;
    double vx;
    double vy;
    double size;
    double radius;
    double jumpGauge;
    double runningTime;
    Game game = new Game();

    public Player(double x, double y){

        this.direction_L = false;
        this.runningTime = 0;
        this.crouching = false;
        this.running_R = false;
        this.running_L = false;
        this.onGround = true;
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.size = 32;
        this.radius = this.size / 2.0 * 1.414;
        this.jumpGauge = 0;
    }

    public AABB aabb(){
        return new AABB(this.x, this.y, this.size, this.size);
    }

    // res를 만들지?
    public double[] getCenter(){
        return new double[] {this.x + this.size/2, this.y + this.size/2};
    }

    public void collideToLeft(double w) {
        this.x = w;
        this.vx *= -1 * Constants.BOUNDFRICTION.getConstant();
        //audios.bounce.start();
    }

    public void collideToRight(double w) {
        this.x = w - this.size;
        this.vx *= -1 * Constants.BOUNDFRICTION.getConstant();
        //audios.bounce.start();
    }

    public void collideToTop(double w) {
        this.y = w - this.size;
        this.vy *= -1 * Constants.BOUNDFRICTION.getConstant();
        //audios.bounce.start();
    }

    public void collideToBottom(double w) {
        this.onGround = true;
        this.y = w;
        this.vx = 0;
        this.vy = 0;
        //audios.landing.start();
    }

    public void  collideToWall(Vector s, Vector r) {
        this.x = s.x;
        this.y = s.y;
        this.vx = r.x * Constants.BOUNDFRICTION.getConstant();
        this.vy = r.y;
        // audios.bounce.start();
        // this.onGround = false;
    }

    public void update() { // 원래 파라미터 : delta
        //Apply previous acceleration
        this.vx *= Constants.GLOBALFRICTION.getConstant();
        this.vy *= Constants.GLOBALFRICTION.getConstant();
        if (Math.abs(this.vx) < 0.0001) this.vx = 0;
        if (Math.abs(this.vy) < 0.0001) this.vy = 0;
        this.x += this.vx;
        if (this.vy != 0) {
            this.y += this.vy - Constants.GRAVITY.getConstant() / 2;
        } else {
            this.y += this.vy;
        }

        PlayerTestCollideRes c;

        //Calculate current level
        this.game.level = (int) Math.floor(this.y / Constants.HEIGHT.getConstant());

        this.game.levelMax = Math.max(this.game.level, this.game.levelMax);
    // 여기부터 시작하기
        // let moving = this.vx * this.vx + this.vy + this.vy;
        // let falling = this.vy < 0 ? true : false;
//        if (keys.ArrowLeft) {
//            this.direction_L = true
//        } else if (keys.ArrowRight) {
//            this.direction_L = false
//        }
//
//        if (this.onGround) {
//            this.vx *= Constants.groundFriction;
//
//            if (keys[' '] && !this.crouching) {
//                this.running_R = false
//                this.running_L = false
//                this.crouching = true;
//            } else if (keys[' '] && this.crouching) {
//                this.jumpGauge >= 1 ? this.jumpGauge = 1 : this.jumpGauge += delta / chargingConst;
//            } else if (keys.ArrowLeft && !this.crouching) {
//                c = this.testCollide(-speed, 0);
//                this.running_R = false
//                this.running_L = true
//                this.runningTime += 1
//                this.runningTime = this.runningTime % 16
//                if (c.side == undefined)
//                    this.vx = -speed;
//                else
//                    this.vx = 0;
//            } else if (keys.ArrowRight && !this.crouching) {
//                this.running_R = true
//                this.running_L = false
//                this.runningTime += 1
//                this.runningTime = this.runningTime % 16
//                c = this.testCollide(speed, 0);
//
//                if (c.side == undefined)
//                    this.vx = speed;
//                else
//                    this.vx = 0;
//            } else if (!keys[' '] && this.crouching) {
//                if (keys.ArrowLeft) this.vx = -sideJump;
//                else if (keys.ArrowRight) this.vx = sideJump;
//                audios.jump.start();
//
//                this.vy = this.jumpGauge * JumpConst * 2;
//                this.jumpGauge = 0;
//                this.onGround = false;
//                this.crouching = false;
//            } else if (!keys.ArrowRight && !keys.ArrowLeft) {
//                this.running_R = false
//                this.running_L = false
//                this.runningTime = 0
//            }
//        }

        //Apply gravity
        c = this.testCollide(0, Constants.GRAVITY.getConstant()*-1);
        if (c.getSide().equals("")) {
            if (this.vy > -100) {
                this.vy -= Constants.GRAVITY.getConstant();
            }

            this.onGround = false;
        }

        //Test if current acceleration make collision happen or not
        c = this.testCollide(this.vx, this.vy);
        if (!c.getSide().equals("")) {
            if (!c.getSide().equals("error"))
                this.responseCollide(c);
        }
    }

    public PlayerTestCollideRes testCollide(double nvx, double nvy) {
        String side = "";
        double set = 0;

        AABB box = this.aabb();
        box.move(nvx, nvy);

        if (box.x < 0) {
            side = "left";
            set = 0;
        }
        else if (box.X > Constants.WIDTH.getConstant()) {
            side = "right";
            set = Constants.WIDTH.getConstant();
        }
        else if (box.y < 0) {
            side = "bottom";
            set = 0;
        }
        else {
            for (Block b : this.game.blocks) {
                if (b.level != this.game.level) continue;

                AABB aabb = b.convert();
                CollideBox r = aabb.checkCollideBox(box);

                if (r.isCollide()) {
                    if (r.isLb() && r.isLt()) {
                        side = "left";
                        set = aabb.X;
                    }
                    else if (r.isRb() && r.isRt()) {
                        side = "right";
                        set = aabb.x;
                    }
                    else if (r.isLb() && r.isRb()) {
                        side = "bottom";
                        set = aabb.Y;
                    }
                    else if (r.isLt() && r.isRt()) {
                        side = "top";
                        set = aabb.y;
                    }
                    else if (r.isLb()) {
                        double bx = box.x - this.vx;
                        if (bx > aabb.X) {
                            side = "left";
                            set = aabb.X;
                        }
                        else {
                            side = "bottom";
                            set = aabb.Y;
                        }
                    }
                    else if (r.isRb()) {
                        double bx = box.X - this.vx;
                        if (bx < aabb.x) {
                            side = "right";
                            set = aabb.x;
                        }
                        else {
                            side = "bottom";
                            set = aabb.Y;
                        }
                    }
                    else if (r.isLt()) {
                        double bx = box.x - this.vx;
                        if (bx > aabb.X) {
                            side = "left";
                            set = aabb.X;
                        }
                        else {
                            side = "top";
                            set = aabb.y;
                        }
                    }
                    else if (r.isRt()) {
                        double bx = box.X - this.vx;
                        if (bx < aabb.x) {
                            side = "right";
                            set = aabb.x;
                        }
                        else {
                            side = "top";
                            set = aabb.y;
                        }
                    }

                    return new PlayerTestCollideRes(side, set);
                }
            }

            for (Wall w : this.game.walls) {
                if (w.level != this.game.level) continue;

                w = w.convert();

                CollideAABB r = w.checkCollideAABB(box, nvx, nvy);

                if (r.getCollide() != null) { // 일단 undefine에서 null로 변경
                    side = "wall";
                    Vector nv = new Vector(nvx, nvy);
                    Vector n;
                    Vector vSet;

                    if (!r.isEndPoint()) {
                        Vector hitPoint = Util.getIntersect(w.x0, w.y0, w.x1, w.y1, r.getCollide().x, r.getCollide().y, r.getCollide().x + nvx, r.getCollide().y + nvy);

                        vSet = new Vector(box.x, box.y).add(hitPoint.sub(r.getCollide()));
                        n = w.getNormal();

                    }
                    else {
                        n = new Vector(w.x0, w.y0).sub(new Vector(w.x1, w.y1));
                        n.normalize();
                        vSet = new Vector(box.x, box.y).sub(nv.mul(3));
                    }

                    Vector ref = nv.sub(n.mul(2).mul(nv.dot(n)));
                    // let ref = nv.sub(n.mul(nv.dot(n)));

                    return new PlayerTestCollideRes(side, vSet, ref); // issue : set이 double이랑 Vector 둘 다 사용되는 문제
                }
            }
        }
        return new PlayerTestCollideRes(side, set);
    }

    public void responseCollide(PlayerTestCollideRes c) {
        switch (c.getSide()) {
            case "left":
                this.collideToLeft(c.getSet());
                break;
            case "right":
                this.collideToRight(c.getSet());
                break;
            case "bottom":
                this.collideToBottom(c.getSet());
                break;
            case "top":
                this.collideToTop(c.getSet());
                break;
            case "wall":
                this.collideToWall(c.getVSet(), c.getRef());
                break;

        }
    }

    // 이것도
    public void render(){

    }
}
