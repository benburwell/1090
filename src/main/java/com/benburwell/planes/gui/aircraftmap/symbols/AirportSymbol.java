package com.benburwell.planes.gui.aircraftmap.symbols;

import com.benburwell.planes.data.Airport;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.AircraftMap;
import com.benburwell.planes.gui.aircraftmap.Drawable;
import com.benburwell.planes.gui.aircraftmap.GeoPoint;

import java.awt.Graphics;

/**
 * @author ben
 */
public class AirportSymbol extends GeoPoint implements Drawable {
    private String name;
    private String iataCode;
    private String localCode;

    public AirportSymbol(Airport airport) {
        super(airport.getLatitude(), airport.getLongitude(), airport.getElevation());
        this.name = airport.getName();
        this.iataCode = airport.getIataCode();
        this.localCode = airport.getLocalCode();
    }

    public String getDisplayName() {
        if (this.iataCode != null && !this.iataCode.isEmpty()) {
            return this.iataCode;
        }
        if (this.localCode != null && !this.localCode.isEmpty()) {
            return this.localCode;
        }
        return this.name;
    }

    @Override
    public void drawOn(Graphics g, AircraftMap map) {
        if (this.shouldDrawOn(map)) {
            int x = this.getX(map);
            int y = this.getY(map);

            g.setColor(GraphicsTheme.Colors.MAGENTA);

            g.drawRect(x - 2, y - 2, 4, 4);
            g.drawString(this.getDisplayName(), x + 6, y);
        }
    }
}
