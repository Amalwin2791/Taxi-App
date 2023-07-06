package com.taxiapp.route;

import java.util.ArrayList;

public class Point {
    private final String pointName;
    private final ArrayList<Path> paths;

    public Point(String pointName) {
        this.pointName = pointName;
        this.paths = new ArrayList<>();
    }

    public void addPath(Point destination, Integer distance) {
        this.paths.add(new Path(this, destination, distance));

    }

    public void removePath(Point destination) {
        this.paths.removeIf(path -> path.getDestination().equals(destination));
    }

    public String getPointName() {
        return this.pointName;
    }

    public ArrayList<Path> getPaths() {
        return this.paths;
    }
    public void print() {
        String message = "";

        if (this.paths.size() == 0) {
            System.out.println(this.pointName + " -->");
            return;
        }

        for(int i = 0; i < this.paths.size(); i++) {
            if (i == 0) {
                message += this.paths.get(i).getSource().getPointName() + " -->  ";
            }

            message += this.paths.get(i).getDestination().getPointName();

            message += " (" + this.paths.get(i).getDistance() + ")";

            if (i != this.paths.size() - 1) {
                message += ", ";
            }
        }
        System.out.println(message);
    }

}
