package com.benburwell.planes.data;

import org.apache.commons.csv.CSVRecord;

/**
 * @author ben
 */
public class Runway extends AbstractCSVReader {
    private int id;
    private int airportId;
    private String airportIdent;
    private Integer length;
    private Integer width;
    private String surface;
    private boolean lighted;
    private boolean closed;
    private String leIdent;
    private Double leLatitude;
    private Double leLongitude;
    private Double leElevation;
    private Double leHeading;
    private Integer leDisplacedThreshold;
    private String heIdent;
    private Double heLatitude;
    private Double heLongitude;
    private Double heElevation;
    private Double heHeading;
    private Integer heDisplacedThreshold;

    @Override
    public void readRecord(CSVRecord record) {
        this.id = Integer.valueOf(record.get("id"));
        this.airportId = Integer.valueOf(record.get("airport_ref"));
        this.airportIdent = record.get("airport_ident");
        try {
            this.length = Integer.valueOf(record.get("length_ft"));
        } catch (NumberFormatException e) {}
        try {
            this.width = Integer.valueOf(record.get("width_ft"));
        } catch (NumberFormatException e) {}
        this.surface = record.get("surface");
        this.lighted = record.get("lighted").equals("1");
        this.closed = record.get("closed").equals("1");
        this.leIdent = record.get("le_ident");
        try {
            this.leLatitude = Double.valueOf(record.get("le_latitude_deg"));
        } catch (NumberFormatException e) {}
        try {
            this.leLongitude = Double.valueOf(record.get("le_longitude_deg"));
        } catch (NumberFormatException e) {}
        try {
            this.leElevation = Double.valueOf(record.get("le_elevation_ft"));
        } catch (NumberFormatException e) {}
        try {
            this.leHeading = Double.valueOf(record.get("le_heading_degT"));
        } catch (NumberFormatException e) {}
        try {
            this.leDisplacedThreshold = Integer.valueOf(record.get("le_displaced_threshold_ft"));
        } catch (NumberFormatException e) {}
        this.heIdent = record.get("he_ident");
        try {
            this.heLatitude = Double.valueOf(record.get("he_latitude_deg"));
        } catch (NumberFormatException e) {}
        try {
            this.heLongitude = Double.valueOf(record.get("he_longitude_deg"));
        } catch (NumberFormatException e) {}
        try {
            this.heElevation = Double.valueOf(record.get("he_elevation_ft"));
        } catch (NumberFormatException e) {}
        try {
            this.heHeading = Double.valueOf(record.get("he_heading_degT"));
        } catch (NumberFormatException e) {}
        try {
            this.heDisplacedThreshold = Integer.valueOf(record.get("he_displaced_threshold_ft"));
        } catch (NumberFormatException e) {}
    }

    public boolean isDrawable() {
        return !this.isClosed() && (this.getLeLatitude() != null) && (this.getLeLongitude() != null) && (this.getHeLatitude() != null) && (this.getHeLongitude() != null);
    }

    public int getId() {
        return id;
    }

    public int getAirportId() {
        return airportId;
    }

    public String getAirportIdent() {
        return airportIdent;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getWidth() {
        return width;
    }

    public String getSurface() {
        return surface;
    }

    public boolean isLighted() {
        return lighted;
    }

    public boolean isClosed() {
        return closed;
    }

    public String getLeIdent() {
        return leIdent;
    }

    public Double getLeLatitude() {
        return leLatitude;
    }

    public Double getLeLongitude() {
        return leLongitude;
    }

    public Double getLeElevation() {
        return leElevation;
    }

    public Double getLeHeading() {
        return leHeading;
    }

    public Integer getLeDisplacedThreshold() {
        return leDisplacedThreshold;
    }

    public String getHeIdent() {
        return heIdent;
    }

    public Double getHeLatitude() {
        return heLatitude;
    }

    public Double getHeLongitude() {
        return heLongitude;
    }

    public Double getHeElevation() {
        return heElevation;
    }

    public Double getHeHeading() {
        return heHeading;
    }

    public Integer getHeDisplacedThreshold() {
        return heDisplacedThreshold;
    }
}
