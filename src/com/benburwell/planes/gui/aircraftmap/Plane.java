package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.gui.GraphicsTheme;

import java.awt.*;

/**
 * Created by ben on 11/19/16.
 */
public class Plane extends GeoPoint implements Drawable {
    private String name;
    private final int DOT_DIMENSION = 4;
    private final int TEXT_OFFSET_X = 10;
    private final int TEXT_OFFSET_Y = 15;

    public Plane(String name, double latitude, double longitude) {
        super(latitude, longitude);
        this.name = name;
    }

    public void drawOn(Graphics g, AircraftMap map) {
        int x = this.getX(map);
        int y = this.getY(map);

        if (x >= 0 && x <= map.getSize().getWidth() && y >= 0 && y <= map.getSize().getHeight()) {
            // draw the plane dot
            g.setColor(GraphicsTheme.Colors.ORANGE);
            g.drawRect(x - (DOT_DIMENSION / 2), y - (DOT_DIMENSION / 2), DOT_DIMENSION, DOT_DIMENSION);

            // draw the name of the plane
            g.setColor(GraphicsTheme.Colors.BLUE);
            g.drawString(this.name, this.getX(map) + TEXT_OFFSET_X, this.getY(map) + TEXT_OFFSET_Y);
        } else {
            System.out.println("Skipping drawing plane at " + x + "," + y + " which is not within bounds");
        }
    }
}
