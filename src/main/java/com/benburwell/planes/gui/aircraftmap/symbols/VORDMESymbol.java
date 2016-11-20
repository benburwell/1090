package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.Drawable;
import com.benburwell.planes.gui.aircraftmap.symbols.VORSymbol;

import java.awt.Graphics;

/**
 * Created by ben on 11/19/16.
 */
public class VORDMESymbol extends VORSymbol implements Drawable {
    public VORDMESymbol(String name, Position pos, int frequency) {
        super(name, pos, frequency);
    }

    @Override
    public void drawOn(Graphics graphicsContext, AircraftMap map) {
        super.drawOn(graphicsContext, map);
        if (this.shouldDrawOn(map)) {
            int x = this.getX(map);
            int y = this.getY(map);
            graphicsContext.setColor(GraphicsTheme.Colors.VIOLET);
            graphicsContext.drawRect(x - VORSymbol.RADIUS, y - VORSymbol.HEIGHT, VORSymbol.RADIUS * 2, VORSymbol.HEIGHT * 2);
        }
    }
}
