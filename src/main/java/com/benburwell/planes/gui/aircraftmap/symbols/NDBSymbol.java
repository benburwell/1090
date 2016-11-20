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
public class NDBSymbol extends GeoPoint implements Drawable {
    public static final int INNER_RADIUS = 3;
    public static final int OUTER_RADIUS = 9;
    public static final int TEXT_OFFSET = 4;

    private String name;
    private int frequency;

    public NDBSymbol(String name, Position pos, int frequency) {
        super(pos.getLatitude(), pos.getLongitude(), pos.getAltitude());
        this.name = name;
        this.frequency = frequency;
    }

    @Override
    public void drawOn(Graphics g, AircraftMap map) {
        if (this.shouldDrawOn(map)) {
            g.setColor(GraphicsTheme.Styles.MAP_NAVAID_COLOR);

            int x = this.getX(map);
            int y = this.getY(map);
            g.fillOval(x - INNER_RADIUS, y - INNER_RADIUS, INNER_RADIUS * 2, INNER_RADIUS * 2);
            g.drawOval(x - OUTER_RADIUS, y - OUTER_RADIUS, OUTER_RADIUS * 2, OUTER_RADIUS * 2);

            g.drawString(this.name, x + OUTER_RADIUS + TEXT_OFFSET, y);
            g.drawString("" + this.frequency, x + OUTER_RADIUS + TEXT_OFFSET, y + g.getFontMetrics().getHeight());
        }
    }
}
