package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Aircraft;
import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.Drawable;
import com.benburwell.planes.gui.aircraftmap.GeoPoint;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by ben on 11/19/16.
 */
public class PlaneSymbol extends GeoPoint implements Drawable {
    public final int TRIANGLE_HEIGHT = 6;
    public final int TRIANGLE_WIDTH = 4;
    public final int TEXT_OFFSET_X = 10;
    public final int TEXT_OFFSET_Y = 15;
    public final int MIN_COLOR_HEIGHT = 0;
    public final int MAX_COLOR_HEIGHT = 50000;
    private String name;
    private double heading;
    private double speed;
    private double verticalRate;

    public PlaneSymbol(Aircraft ac) {
        this(ac.getCallsign(), ac.getCurrentPosition(), ac.getTrack(), ac.getGroundSpeed(), ac.getVerticalRate());
    }

    public PlaneSymbol(String name, Position position, double heading, double speed, double verticalRate) {
        this(name, position.getLatitude(), position.getLongitude(), position.getAltitude(), heading, speed, verticalRate);
    }

    public PlaneSymbol(String name, double latitude, double longitude, double altitude, double heading, double speed, double verticalRate) {
        super(latitude, longitude, altitude);
        this.name = name;
        this.heading = heading;
        this.speed = speed;
        this.verticalRate = verticalRate;
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

    public double getSpeed() {
        return this.speed;
    }

    public void drawTriangle(Graphics2D ctx, int x, int y, int predictionLength) {
        AffineTransform at = new AffineTransform();
        at.setToRotation(this.getAngle(), x, y);
        ctx.setTransform(at);
        int[] xs = new int[]{ x - TRIANGLE_WIDTH, x, x + TRIANGLE_WIDTH, x - TRIANGLE_WIDTH };
        int[] ys = new int[]{ y + TRIANGLE_HEIGHT, y - TRIANGLE_HEIGHT, y + TRIANGLE_HEIGHT, y + TRIANGLE_HEIGHT };
        ctx.fillPolygon(xs, ys, 4);
        ctx.drawLine(x, y, x, y - predictionLength);
    }

    public String getVerticalRateIndicator() {
        if (this.verticalRate > 0) {
            return "\u2191"; // ↑
        } else if (this.verticalRate < 0) {
            return "\u2193"; // ↓
        }
        return "";
    }

    public String getDisplayName() {
        if (this.name == null || this.name.isEmpty()) {
            return "-----";
        }
        return this.name;
    }

    public int getPredictionLength(double pixelsPerNauticalMile) {
        return (int) (this.speed / 60.0 * pixelsPerNauticalMile);
    }

    public void drawOn(Graphics g, AircraftMap map) {
        if (this.shouldDrawOn(map)) {
            int x = this.getX(map);
            int y = this.getY(map);

            // draw the plane dot
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(this.getPlaneColor());
            int predictedTrack = this.getPredictionLength(map.getPixelsPerNauticalMile());
            this.drawTriangle(g2d, x, y, predictedTrack);
            g2d.dispose();


            // draw the name of the plane
            g.setColor(GraphicsTheme.Colors.BASE_5);
            g.drawString(this.getDisplayName(), x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y);
            String infoString = String.format("%d%s %.1f", this.getFlightLevel(), this.getVerticalRateIndicator(), this.getSpeed());
            g.drawString(infoString,  x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y + g.getFontMetrics().getHeight());
        }
    }
}
