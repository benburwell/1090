package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.graph.Airway;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.Drawable;
import com.benburwell.planes.gui.aircraftmap.GeoPoint;

import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ben
 */
public class RouteSymbol implements Drawable {
    private final Airway airway;

    public RouteSymbol(Airway airway) {
        this.airway = airway;
    }

    private boolean inRange(int lo, int n, int hi) {
        return lo <= n && n <= hi;
    }

    @Override
    public void drawOn(Graphics graphicsContext, AircraftMap map) {
        int w = map.getWidth();
        int h = map.getHeight();
        graphicsContext.setColor(GraphicsTheme.Styles.MAP_ROUTE_COLOR);
        List<GeoPoint> points = this.airway.getPoints().stream().map(GeoPoint::new).collect(Collectors.toList());
        for (int i = 0; i < points.size() - 1; i++) {
            GeoPoint p1 = points.get(i);
            int p1x = p1.getX(map);
            int p1y = p1.getY(map);

            GeoPoint p2 = points.get(i + 1);
            int p2x = p2.getX(map);
            int p2y = p2.getY(map);

            boolean p1OnMap = inRange(0, p1x, w) && inRange(0, p1y, h);
            boolean p2OnMap = inRange(0, p2x, w) && inRange(0, p2y, h);
            boolean showEdge = p1OnMap || p2OnMap;
            if (showEdge) {
                graphicsContext.drawLine(p1x, p1y, p2x, p2y);
                graphicsContext.fillOval(p1x - 2, p1y - 2, 4, 4);
            }
        }
        int pnx = points.get(points.size() - 1).getX(map);
        int pny = points.get(points.size() - 1).getY(map);
        if (inRange(0, pnx, w) && inRange(0, pny, h)) {
            graphicsContext.fillOval(pnx - 2, pny - 2, 4, 4);
        }
    }
}
