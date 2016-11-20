package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.data.Position;

/**
 * @author ben
 */
public class GeoPoint {
    private double latitude;
    private double longitude;
    private double altitude;

    public GeoPoint(Position position) {
        this.latitude = position.getLatitude();
        this.longitude = position.getLongitude();
        this.altitude = position.getAltitude();
    }

    public GeoPoint(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public int getX(AircraftMap map) {
        double degreesFromCenter = map.getCenterLongitude() - this.longitude;
        double pixelsFromCenter = degreesFromCenter * map.getPixelsPerDegreeLongitude();
        double centerPixels = map.getSize().getWidth() / 2;
        int xPosition = (int) (centerPixels - pixelsFromCenter);
        return xPosition;
    }

    public int getY(AircraftMap map) {
        double degreesFromCenter = map.getCenterLatitude() - this.latitude;
        double pixelsFromCenter = degreesFromCenter * map.getPixelsPerDegreeLatitude();
        double centerPixels = map.getSize().getHeight() / 2;
        int yPosition = (int) (centerPixels + pixelsFromCenter);
        return yPosition;
    }

    public boolean shouldDrawOn(AircraftMap map) {
        int x = this.getX(map);
        int y = this.getY(map);
        return (x >= 0 && x <= map.getWidth() && y >= 0 && y <= map.getHeight());
    }

    public double getAltitude() {
        return this.altitude;
    }
}
