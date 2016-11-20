package com.benburwell.planes.graph;

import com.benburwell.planes.data.Intersection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ben
 */
public class RouteGraph {
    private Map<String,Airway> airways = new HashMap<>();

    public void addIntersection(Intersection in) {
        if (!this.airways.containsKey(in.getAirway1())) {
            this.airways.put(in.getAirway1(), new Airway(in.getAirway1()));
        }
        if (!this.airways.containsKey(in.getAirway2())) {
            this.airways.put(in.getAirway2(), new Airway(in.getAirway2()));
        }
        this.airways.get(in.getAirway1()).addPoint(in.getLatitude(), in.getLongitude());
        this.airways.get(in.getAirway2()).addPoint(in.getLatitude(), in.getLongitude());
    }

    public Collection<Airway> getAirways() {
        return this.airways.values();
    }
}
