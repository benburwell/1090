package com.benburwell.planes.data;

import org.apache.commons.csv.CSVRecord;

/**
 * Frequencies in kHz, elevations in ft
 *
 * Created by ben on 11/19/16.
 */
public class NavigationAid {
    private int id;
    private String filename;
    private String ident;
    private String name;
    private String type;
    private int frequency;
    private double latitude;
    private double longitude;
    private int elevation;
    private String isoCountry;
    private double dmeFrequency;
    private String dmeChannel;
    private double dmeLatitude;
    private double dmeLongitude;
    private int dmeElevation;
    private double slavedVariation;
    private double magneticVariation;
    private String usageType;
    private String power;
    private String associatedAirport;

    public NavigationAid(CSVRecord record) {
        this.setId(Integer.valueOf(record.get("id")));
        this.setFilename(record.get("filename"));
        this.setIdent(record.get("ident"));
        this.setName(record.get("name"));
        this.setType(record.get("type"));

        try {
            this.setFrequency(Integer.valueOf(record.get("frequency_khz")));
        } catch (NumberFormatException ignored) {}

        try {
            this.setLatitude(Double.valueOf(record.get("latitude_deg")));
        } catch (NumberFormatException ignored) {}

        try {
            this.setLongitude(Double.valueOf(record.get("longitude_deg")));
        } catch (NumberFormatException ignored) {}

        try {
            this.setElevation(Integer.valueOf(record.get("elevation_ft")));
        } catch (NumberFormatException ignored) {}

        this.setIsoCountry(record.get("iso_country"));

        try {
            this.setDmeFrequency(Double.valueOf(record.get("dme_frequency_khz")));
        } catch (NumberFormatException ignored) {}

        this.setDmeChannel(record.get("dme_channel"));

        try {
            this.setDmeLatitude(Double.valueOf(record.get("dme_latitude_deg")));
        } catch (NumberFormatException ignored) {}

        try {
            this.setDmeLongitude(Double.valueOf(record.get("dme_longitude_deg")));
        } catch (NumberFormatException ignored) {}

        try {
            this.setDmeElevation(Integer.valueOf(record.get("dme_elevation_ft")));
        } catch (NumberFormatException ignored) {}

        try {
            this.setSlavedVariation(Double.valueOf(record.get("slaved_variation_deg")));
        } catch (NumberFormatException ignored) {}

        try {
            this.setMagneticVariation(Double.valueOf(record.get("magnetic_variation_deg")));
        } catch (NumberFormatException ignored) {}

        this.setUsageType(record.get("usageType"));
        this.setPower(record.get("power"));
        this.setAssociatedAirport(record.get("associated_airport"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public double getDmeFrequency() {
        return dmeFrequency;
    }

    public void setDmeFrequency(double dmeFrequency) {
        this.dmeFrequency = dmeFrequency;
    }

    public String getDmeChannel() {
        return dmeChannel;
    }

    public void setDmeChannel(String dmeChannel) {
        this.dmeChannel = dmeChannel;
    }

    public double getDmeLatitude() {
        return dmeLatitude;
    }

    public void setDmeLatitude(double dmeLatitude) {
        this.dmeLatitude = dmeLatitude;
    }

    public double getDmeLongitude() {
        return dmeLongitude;
    }

    public void setDmeLongitude(double dmeLongitude) {
        this.dmeLongitude = dmeLongitude;
    }

    public int getDmeElevation() {
        return dmeElevation;
    }

    public void setDmeElevation(int dmeElevation) {
        this.dmeElevation = dmeElevation;
    }

    public double getSlavedVariation() {
        return slavedVariation;
    }

    public void setSlavedVariation(double slavedVariation) {
        this.slavedVariation = slavedVariation;
    }

    public double getMagneticVariation() {
        return magneticVariation;
    }

    public void setMagneticVariation(double magneticVariation) {
        this.magneticVariation = magneticVariation;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getAssociatedAirport() {
        return associatedAirport;
    }

    public void setAssociatedAirport(String associatedAirport) {
        this.associatedAirport = associatedAirport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
