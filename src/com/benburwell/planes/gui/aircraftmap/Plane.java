package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by ben on 11/19/16.
 */
public class Plane extends GeoPoint implements Drawable {
    public final int TRIANGLE_HEIGHT = 8;
    public final int TRIANGLE_WIDTH = 4;
    public final int TEXT_OFFSET_X = 10;
    public final int TEXT_OFFSET_Y = 15;
    public final int MIN_COLOR_HEIGHT = 0;
    public final int MAX_COLOR_HEIGHT = 50000;
    private String name;
    private double heading;

    public Plane(String name, Position position, double heading) {
        this(name, position.getLatitude(), position.getLongitude(), position.getAltitude(), heading);
    }

    public Plane(String name, double latitude, double longitude, double altitude, double heading) {
        super(latitude, longitude, altitude);
        this.name = name;
        this.heading = heading;
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

    public double getAngle() {
        return Math.toRadians(this.heading);
    }

    public void drawTriangle(Graphics2D ctx, int x, int y) {
        AffineTransform at = new AffineTransform();
        //at.setToRotation(this.getAngle(), x, y);
        at.setToRotation(this.getAngle(), x, y);
        ctx.setTransform(at);
        int[] xs = new int[]{ x - TRIANGLE_WIDTH, x, x + TRIANGLE_WIDTH, x - TRIANGLE_WIDTH };
        int[] ys = new int[]{ y + TRIANGLE_HEIGHT, y - TRIANGLE_HEIGHT, y + TRIANGLE_HEIGHT, y + TRIANGLE_HEIGHT };
        ctx.drawPolyline(xs, ys, 4);
    }

    public void drawOn(Graphics g, AircraftMap map) {
        int x = this.getX(map);
        int y = this.getY(map);

        if (x >= 0 && x <= map.getSize().getWidth() && y >= 0 && y <= map.getSize().getHeight()) {
            // draw the plane dot
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(this.getPlaneColor());
            this.drawTriangle(g2d, x, y);
            g2d.dispose();


            // draw the name of the plane
            g.setColor(GraphicsTheme.Colors.BASE_5);
            g.drawString(this.name, x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y);
            g.drawString("" + this.getFlightLevel(), x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y + g.getFontMetrics().getHeight());
        }
    }
}
