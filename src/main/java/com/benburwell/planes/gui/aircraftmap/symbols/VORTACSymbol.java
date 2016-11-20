package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.Drawable;

import java.awt.Graphics;

/**
 * @author ben
 */
public class VORTACSymbol extends VORSymbol implements Drawable {
    public VORTACSymbol(String name, Position pos, int frequency) {
        super(name, pos, frequency);
    }

    @Override
    public void drawOn(Graphics graphicsContext, AircraftMap map) {
        super.drawOn(graphicsContext, map);
        if (this.shouldDrawOn(map)) {
            int x = this.getX(map);
            int y = this.getY(map);
            graphicsContext.setColor(GraphicsTheme.Styles.MAP_NAVAID_COLOR);

            int[] xs = { x - VORSymbol.X_OFFSET, x + VORSymbol.X_OFFSET, x + VORSymbol.X_OFFSET, x - VORSymbol.X_OFFSET, x - VORSymbol.X_OFFSET };
            int[] ys = { y + VORSymbol.RADIUS, y + VORSymbol.RADIUS, y + VORSymbol.RADIUS * 2, y + VORSymbol.RADIUS * 2, y + VORSymbol.RADIUS };
            graphicsContext.fillPolygon(xs, ys, 5);
        }
    }
}
