package com.benburwell.planes.gui.aircraftmap;

/**
 * @author ben
 */
public enum DisplayMode {
    HIDDEN,
    DETAILED,
    BASIC;

    public DisplayMode next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}
