package com.benburwell.planes.gui.aircraftmap;

import java.awt.Graphics;

/**
 * @author ben
 */
public interface Drawable {
    void drawOn(Graphics graphicsContext, AircraftMap map);
}
