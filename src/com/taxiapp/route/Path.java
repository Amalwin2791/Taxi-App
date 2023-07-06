package com.taxiapp.route;

public class Path {
    private final Point source;
    private final Point destination;
    private final Integer distance;

    public Path(Point source, Point destination, Integer distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Point getSource() {
        return this.source;
    }

    public Point getDestination() {
        return this.destination;
    }

    public Integer getDistance() {
        return this.distance;
    }


}
