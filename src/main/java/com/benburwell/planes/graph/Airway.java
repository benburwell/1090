package com.benburwell.planes.graph;

import com.benburwell.planes.data.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ben
 */
public class Airway {
    private String code;
    private List<Position> points = new ArrayList<>();

    public Airway(String code) {
        this.code = code;
    }

    public void addPoint(double latitude, double longitude) {
        Position pos = new Position();
        pos.setLatitude(latitude);
        pos.setLongitude(longitude);
        this.points.add(pos);
    }

    public List<Position> getPoints() {
        return this.points;
    }
}
