package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.DisplayMode;
import com.benburwell.planes.gui.aircraftmap.Drawable;

import java.awt.Graphics;

/**
 * @author ben
 */
public class VORDMESymbol extends VORSymbol implements Drawable {
    public VORDMESymbol(String name, Position pos, int frequency) {
        super(name, pos, frequency);
    }

    @Override
    public void drawOn(Graphics graphicsContext, AircraftMap map, DisplayMode displayMode) {
        super.drawOn(graphicsContext, map, displayMode);
        if (this.shouldDrawOn(map) && !displayMode.equals(DisplayMode.HIDDEN)) {
            int x = this.getX(map);
            int y = this.getY(map);
            graphicsContext.setColor(GraphicsTheme.Styles.MAP_NAVAID_COLOR);
            graphicsContext.drawRect(x - VORSymbol.RADIUS, y - VORSymbol.HEIGHT, VORSymbol.RADIUS * 2, VORSymbol.HEIGHT * 2);
        }
    }
}
