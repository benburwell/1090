package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.Drawable;
import com.benburwell.planes.gui.aircraftmap.GeoPoint;

import java.awt.Graphics;

/**
 * @author ben
 */
public class VORSymbol extends GeoPoint implements Drawable {
    public static final int RADIUS = 11;
    public static final int HEIGHT = (int) (Math.sin(Math.toRadians(60)) * RADIUS);
    public static final int X_OFFSET = (int) (Math.cos(Math.toRadians(60)) * RADIUS);
    public static final int DOT_RADIUS = 3;
    public static final int TEXT_OFFSET = 4;

    private String name;
    private int frequency;

    public VORSymbol(String name, Position pos, int frequency) {
        super(pos.getLatitude(), pos.getLongitude(), pos.getAltitude());
        this.name = name;
        this.frequency = frequency;
    }

    @Override
    public void drawOn(Graphics graphicsContext, AircraftMap map) {
        if (this.shouldDrawOn(map)) {
            int x = this.getX(map);
            int y = this.getY(map);
            graphicsContext.setColor(GraphicsTheme.Colors.VIOLET);

            // center dot
            graphicsContext.fillOval(x - DOT_RADIUS, y - DOT_RADIUS, DOT_RADIUS * 2, DOT_RADIUS * 2);

            // hexagon
            int[] xs = { x - RADIUS, x - X_OFFSET, x + X_OFFSET, x + RADIUS, x + X_OFFSET, x - X_OFFSET, x - RADIUS };
            int[] ys = { y, y - HEIGHT, y - HEIGHT, y, y + HEIGHT, y + HEIGHT, y };
            graphicsContext.drawPolygon(xs, ys, 7);

            graphicsContext.drawString(this.name, x + RADIUS + TEXT_OFFSET, y);
            graphicsContext.drawString(String.format("%.3f", this.frequency / 1000.0), x + RADIUS + TEXT_OFFSET, y + graphicsContext.getFontMetrics().getHeight());
        }
    }
}
