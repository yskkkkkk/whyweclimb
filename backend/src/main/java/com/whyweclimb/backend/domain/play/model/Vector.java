package com.whyweclimb.backend.domain.play.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vector {
    double x;
    double y;

    public void normalize(){
        double len = this.getLength();

        this.x /= len;
        this.y /= len;
    }

    public double getLength(){
        return Math.sqrt(this.x * this.x + this.y + this.y);
    }

    public double dot(Vector v){
        return this.x * v.x + this.y * v.y;
    }

    public double cross(Vector v) {
        return this.y * v.x - this.x * v.y;
    }

    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y);
    }

    public Vector sub(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y);
    }

    public Vector div(double v) {
        return new Vector(this.x / v, this.y / v);
    }

    public Vector mul(double v) {
        return new Vector(this.x * v, this.y * v);
    }

    public boolean equals(Vector v) {
        return this.x == v.x && this.y == v.y;
    }
}
