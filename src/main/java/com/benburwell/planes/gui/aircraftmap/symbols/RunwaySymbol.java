package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Runway;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.DisplayMode;
import com.benburwell.planes.gui.aircraftmap.Drawable;
import com.benburwell.planes.gui.aircraftmap.GeoPoint;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author ben
 */
public class RunwaySymbol implements Drawable {
    public final double FEET_PER_MILE = 6076.12;
    private Runway runway;

    public RunwaySymbol(Runway r) {
        this.runway = r;
    }

    @Override
    public void drawOn(Graphics g, AircraftMap map, DisplayMode displayMode) {
        if (!displayMode.equals(DisplayMode.HIDDEN)) {
            GeoPoint he = new GeoPoint(this.runway.getHeLatitude(), this.runway.getHeLongitude(), 0);
            int hex = he.getX(map);
            int hey = he.getY(map);

            GeoPoint le = new GeoPoint(this.runway.getLeLatitude(), this.runway.getLeLongitude(), 0);
            int lex = le.getX(map);
            int ley = le.getY(map);

            if (he.shouldDrawOn(map) && le.shouldDrawOn(map)) {
                //if (this.runway.getWidth() != null && this.runway.getLength() != null) {
                //    double dx = hex - lex;
                //    double dy = hey - ley;
                //    int width = (int) ((this.runway.getWidth() / FEET_PER_MILE) * map.getPixelsPerNauticalMile());
                //    int length = (int) ((this.runway.getLength() / FEET_PER_MILE) * map.getPixelsPerNauticalMile());

                //    AffineTransform at = new AffineTransform();
                //    at.setToRotation(Math.atan(dy / dx), lex, ley);
                //    Graphics2D g2d = (Graphics2D) g.create();
                //    g2d.setColor(GraphicsTheme.Styles.MAP_RUNWAY_BORDER_COLOR);
                //    g2d.setTransform(at);

                //    g2d.drawRect(lex - (width / 2), ley - (length / 2), width, length);
                //    g2d.dispose();
                //}

                g.setColor(GraphicsTheme.Styles.MAP_RUNWAY_BORDER_COLOR);
                g.drawLine(lex, ley, hex, hey);

                if (displayMode.equals(DisplayMode.DETAILED)) {
                    g.drawString(runway.getLeIdent(), lex, ley);
                    g.drawString(runway.getHeIdent(), hex, hey);
                }
            }
        }
    }
}
