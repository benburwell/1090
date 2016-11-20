package com.benburwell.planes.data;

import java.util.Date;

/**
 * @author ben
 */
public class Position {
    private Date timestamp = new Date(System.currentTimeMillis());
    private Double latitude = 0.0;
    private Double longitude = 0.0;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    private double altitude = 0;
}
