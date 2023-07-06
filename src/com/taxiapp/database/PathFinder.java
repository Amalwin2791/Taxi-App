package com.taxiapp.database;

import com.taxiapp.route.Path;
import com.taxiapp.route.Point;
import com.taxiapp.route.QueueObject;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.PriorityQueue;

 public class PathFinder {
     private final static Route route = Database.getInstance().getRoute();
    public static Dictionary[] dijkstra( Point source) {
        Dictionary<String, Integer> distances = new Hashtable<>();
        Dictionary<String, Point> previous = new Hashtable<>();
        PriorityQueue<QueueObject> queue = new PriorityQueue<>();

        queue.add(new QueueObject(source, 0));

        for (Point v: route.getPoints() ) {
            if (v != source) {
                distances.put(v.getPointName(), Integer.MAX_VALUE);
            }
            previous.put(v.getPointName(), new Point("Null"));
        }

        distances.put(source.getPointName(), 0);

        while (queue.size() != 0) {
            Point current = queue.poll().point;
            for (Path e: current.getPaths()) {
                Integer alternative = distances.get(current.getPointName()) + e.getDistance();
                String neighborValue = e.getDestination().getPointName();
                if (alternative < distances.get(neighborValue)) {
                    distances.put(neighborValue, alternative);
                    previous.put(neighborValue, current);
                    queue.add(new QueueObject(e.getDestination(), distances.get(neighborValue)));
                }
            }
        }
        return new Dictionary[]{distances, previous};
    }

    public static int shortestPathBetween( int start, int end) {
        Point source = route.getPoints().get(start);
        Point destination =route.getPoints().get(end);
        Dictionary[] dijkstraDictionaries = dijkstra( source);
        Dictionary distances = dijkstraDictionaries[0];
        Dictionary previous = dijkstraDictionaries[1];

        Integer distance = (Integer) distances.get(destination.getPointName());
        return distance;

    }
}