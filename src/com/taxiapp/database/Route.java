package com.taxiapp.database;

import com.taxiapp.route.Point;

import java.util.ArrayList;


 class Route {
    private final ArrayList<Point> points;
    public Route() {
        this.points = new ArrayList<>();
    }

    public Point addPoint(String pointName) {
        Point newPoint = new Point(pointName);
        this.points.add(newPoint);
        return newPoint;
    }

    public void addPath(Point source, Point destination, Integer distance) {
        source.addPath(destination, distance);
        destination.addPath(source, distance);
    }

     ArrayList<Point> getPoints() {
        return this.points;
    }

}

