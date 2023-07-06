package com.taxiapp.route;

public class QueueObject implements Comparable<QueueObject> {
    public Point point;
    public int priority;

    public QueueObject(Point v, int p) {
        this.point = v;
        this.priority = p;
    }

    @Override
    public int compareTo(QueueObject that) {
        if (this.priority == that.priority) {
            return 0;
        } else if (this.priority > that.priority) {
            return 1;
        } else {
            return -1;
        }
    }
}
