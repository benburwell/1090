package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;

import java.awt.*;

/**
 * Created by ben on 11/19/16.
 */
public class Plane extends GeoPoint implements Drawable {
    public final int DOT_DIMENSION = 4;
    public final int TEXT_OFFSET_X = 10;
    public final int TEXT_OFFSET_Y = 15;
    public final int MIN_COLOR_HEIGHT = 0;
    public final int MAX_COLOR_HEIGHT = 50000;
    private String name;

    public Plane(String name, Position position) {
        this(name, position.getLatitude(), position.getLongitude(), position.getAltitude());
    }

    public Plane(String name, double latitude, double longitude, double altitude) {
        super(latitude, longitude, altitude);
        this.name = name;
    }

    public int getFlightLevel() {
        return (int) this.getAltitude() / 100;
    }

    public Color getPlaneColor() {
        Color minColor = GraphicsTheme.Colors.RED;
        Color maxColor = GraphicsTheme.Colors.GREEN;

        float[] minHsb = Color.RGBtoHSB(minColor.getRed(), minColor.getGreen(), minColor.getBlue(), null);
        float[] maxHsb = Color.RGBtoHSB(maxColor.getRed(), maxColor.getGreen(), maxColor.getBlue(), null);

        float minHue = minHsb[0];
        float maxHue = maxHsb[0];
        float minSat = minHsb[1];
        float maxSat = maxHsb[1];
        float minBright = minHsb[2];
        float maxBright = maxHsb[2];

        double planePosition = (this.getAltitude() / (MAX_COLOR_HEIGHT - MIN_COLOR_HEIGHT)) + MIN_COLOR_HEIGHT;
        float huePosition = (float) (planePosition * (maxHue - minHue) + minHue);
        float satPosition = (float) (planePosition * (maxSat - minSat) + minSat);
        float brightPosition = (float) (planePosition * (maxBright - minBright) + minBright);

        Color c = Color.getHSBColor(huePosition, satPosition, brightPosition);
        return c;
    }

    public void drawOn(Graphics g, AircraftMap map) {
        int x = this.getX(map);
        int y = this.getY(map);

        if (x >= 0 && x <= map.getSize().getWidth() && y >= 0 && y <= map.getSize().getHeight()) {
            // draw the plane dot
            g.setColor(this.getPlaneColor());
            g.drawRect(x - (DOT_DIMENSION / 2), y - (DOT_DIMENSION / 2), DOT_DIMENSION, DOT_DIMENSION);

            // draw the name of the plane
            g.setColor(GraphicsTheme.Colors.BLUE);
            g.drawString(this.name, x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y);
            g.drawString("" + this.getFlightLevel(), x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y + g.getFontMetrics().getHeight());
        } else {
            System.out.println("Skipping drawing plane at " + x + "," + y + " which is not within bounds");
        }
    }
}
