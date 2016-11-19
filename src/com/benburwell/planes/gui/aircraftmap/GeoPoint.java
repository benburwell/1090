package com.benburwell.planes.gui.aircraftmap;

/**
 * Created by ben on 11/19/16.
 */
public class GeoPoint {
    private double latitude;
    private double longitude;
    private double altitude;

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

    public double getAltitude() {
        return this.altitude;
    }
}
