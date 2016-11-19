package com.benburwell.planes.data;

/**
 * Frequencies in kHz, elevations in ft
 *
 * Created by ben on 11/19/16.
 */
public class NavigationAid {
    private int id;
    private String filename;
    private String ident;
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

    public NavigationAid() {}

    public static NavigationAid fromCSV(String row) {
        NavigationAid aid = new NavigationAid();
        return aid;
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
}
