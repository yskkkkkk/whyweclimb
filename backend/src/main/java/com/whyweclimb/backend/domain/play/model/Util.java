package com.whyweclimb.backend.domain.play.model;

public class Util {
    public static Vector getIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
        double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        double y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        return new Vector(x,y);
    }
}
