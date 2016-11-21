package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Aircraft;
import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.DisplayMode;
import com.benburwell.planes.gui.aircraftmap.Drawable;
import com.benburwell.planes.gui.aircraftmap.GeoPoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

/**
 * @author ben
 */
public class PlaneSymbol extends GeoPoint implements Drawable {
    public final int TRIANGLE_HEIGHT = 6;
    public final int TRIANGLE_WIDTH = 4;
    public final int TEXT_OFFSET_X = 10;
    public final int TEXT_OFFSET_Y = 15;
    public final int MIN_COLOR_HEIGHT = 0;
    public final int MAX_COLOR_HEIGHT = 50000;
    public final long HISTORY_MILLIS = 5 * 60 * 1000;
    private String name;
    private double heading;
    private double speed;
    private double verticalRate;
    private List<Position> historyTrack = new ArrayList<>();

    public PlaneSymbol(Aircraft ac) {
        super(ac.getCurrentPosition());
        this.name = ac.getCallsign();
        this.heading = ac.getTrack();
        this.speed = ac.getGroundSpeed();
        this.verticalRate = ac.getVerticalRate();

        Date minTime = new Date(System.currentTimeMillis() - HISTORY_MILLIS);
        ac.getPositionHistory().stream()
            .filter(pos -> pos.getTimestamp().after(minTime))
            .forEach(pos -> historyTrack.add(pos));
    }

    public int getFlightLevel() {
        return (int) this.getAltitude() / 100;
    }

    public Color getPlaneColor() {
        Color minColor = GraphicsTheme.Styles.MAP_PLANE_MIN_COLOR;
        Color maxColor = GraphicsTheme.Styles.MAP_PLANE_MAX_COLOR;

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

    @Override
    public void drawOn(Graphics g, AircraftMap map, DisplayMode displayMode) {
        if (this.shouldDrawOn(map) && !displayMode.equals(DisplayMode.HIDDEN)) {
            int x = this.getX(map);
            int y = this.getY(map);

            // draw the history track
            g.setColor(GraphicsTheme.Styles.MAP_PLANE_TRACK_COLOR);
            this.historyTrack.forEach(pos -> {
                GeoPoint p = new GeoPoint(pos);
                g.fillRect(p.getX(map) - 2, p.getY(map) - 2, 4, 4);
            });

            // draw the plane dot
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(this.getPlaneColor());
            int predictedTrack = this.getPredictionLength(map.getPixelsPerNauticalMile());
            this.drawTriangle(g2d, x, y, predictedTrack);
            g2d.dispose();

            // draw the name of the plane
            if (displayMode.equals(DisplayMode.DETAILED)) {
                g.setColor(GraphicsTheme.Styles.MAP_LABEL_COLOR);
                g.drawString(this.getDisplayName(), x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y);
                String infoString = String.format("%d%s %.1f", this.getFlightLevel(), this.getVerticalRateIndicator(), this.getSpeed());
                g.drawString(infoString, x + TEXT_OFFSET_X, y + TEXT_OFFSET_Y + g.getFontMetrics().getHeight());
            }
        }
    }
}
