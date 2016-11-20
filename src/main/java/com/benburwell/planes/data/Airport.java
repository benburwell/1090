package com.benburwell.planes.data;

import org.apache.commons.csv.CSVRecord;

/**
 * Created by ben on 11/19/16.
 */
public class Airport {
    private int id;
    private String ident;
    private String type;
    private String name;
    private double latitude;
    private double longitude;
    private int elevation;
    private String continent;
    private String isoCountry;
    private String isoRegion;
    private String municipality;
    private boolean scheduledService;
    private String gpsCode;
    private String iataCode;
    private String localCode;
    private String homeLink;
    private String wikipediaLink;
    private String keywords;

    public Airport(CSVRecord record) {
        this.setId(Integer.valueOf(record.get("id")));
        this.setIdent(record.get("ident"));
        this.setType(record.get("type"));
        this.setName(record.get("name"));
        try {
            this.setLatitude(Double.valueOf(record.get("latitude_deg")));
        } catch (NumberFormatException ignored) {}
        try {
            this.setLongitude(Double.valueOf(record.get("longitude_deg")));
        } catch (NumberFormatException ignored) {}
        try {
            this.setElevation(Integer.valueOf(record.get("elevation_ft")));
        } catch (NumberFormatException ignored) {}
        this.setContinent(record.get("continent"));
        this.setIsoCountry(record.get("iso_country"));
        this.setIsoRegion(record.get("iso_region"));
        this.setMunicipality(record.get("municipality"));
        this.setScheduledService(record.get("scheduled_service").equals("yes"));
        this.setGpsCode(record.get("gps_code"));
        this.setIataCode(record.get("iata_code"));
        this.setLocalCode(record.get("local_code"));
        this.setHomeLink(record.get("home_link"));
        this.setWikipediaLink(record.get("wikipedia_link"));
        this.setKeywords(record.get("keywords"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public String getIsoRegion() {
        return isoRegion;
    }

    public void setIsoRegion(String isoRegion) {
        this.isoRegion = isoRegion;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public boolean isScheduledService() {
        return scheduledService;
    }

    public void setScheduledService(boolean scheduledService) {
        this.scheduledService = scheduledService;
    }

    public String getGpsCode() {
        return gpsCode;
    }

    public void setGpsCode(String gpsCode) {
        this.gpsCode = gpsCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

    public void setWikipediaLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
