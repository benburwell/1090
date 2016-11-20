package com.benburwell.planes.data;

import org.apache.commons.csv.CSVRecord;

/**
 * @author ben
 */
public class Intersection extends AbstractCSVReader {
    private String airway1;
    private String airway2;
    private String center;
    private double latitude;
    private double longitude;
    private int numberedFix;

    @Override
    public void readRecord(CSVRecord record) {
        String[] airways = record.get("Airways").split(" ");
        this.airway1 = airways[0];
        this.airway2 = airways[1];
        this.center = record.get("Center");
        this.latitude = Double.valueOf(record.get("Latitude"));
        this.longitude = Double.valueOf(record.get("Longitude"));
        this.numberedFix = Integer.valueOf(record.get("Numbered Fix"));
    }

    public String getAirway1() {
        return airway1;
    }

    public String getAirway2() {
        return airway2;
    }

    public String getCenter() {
        return center;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getNumberedFix() {
        return numberedFix;
    }
}
