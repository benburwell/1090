package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.data.Airport;
import com.benburwell.planes.data.NavigationAid;
import com.benburwell.planes.data.Position;
import com.benburwell.planes.data.Runway;
import com.benburwell.planes.graph.RouteGraph;
import com.benburwell.planes.gui.GraphicsTheme;
import com.benburwell.planes.gui.aircraftmap.symbols.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ben
 */
public class AircraftMap extends JPanel {
    // geographic constants
    public final double MAX_LATITUDE = 90.0;
    public final double MIN_LATITUDE = -90.0;
    public final double MAX_LONGITUDE = 180.0;
    public final double MIN_LONGITUDE = -180.0;
    public final double NAUTICAL_MILES_PER_DEGREE_LATITUDE = 60.0;

    // drawing constants
    public final float FONT_SIZE = 12;
    public final int TEXT_PADDING = 5;
    public final int NUMBER_OF_RANGE_RINGS = 5;

    // map manipulation constants
    public final int MIN_ZOOM_PIXELS_PER_MILE = 1;
    public final int MAX_ZOOM_PIXELS_PER_MILE = 2000;
    public final double PAN_INTERVAL_MILES = 1.0;

    // airports
    public final Set<String> VALID_AIRPORT_TYPES = new HashSet<>(Arrays.asList(new String[]{
        "large_airport",
        "medium_airport",
        "small_airport",
        "heliport",
        "seaplane_base"
    }));

    // instance fields
    private List<Drawable> planes = new ArrayList<>();
    private List<Drawable> navaids = new ArrayList<>();
    private List<Drawable> airports = new ArrayList<>();
    private List<Drawable> routes = new ArrayList<>();
    private List<Drawable> runways = new ArrayList<>();
    private double centerLatitude;
    private double centerLongitude;
    private int pixelsPerNauticalMile = 10;

    private DisplayMode navaidDisplayMode = DisplayMode.DETAILED;
    private DisplayMode airportDisplayMode = DisplayMode.DETAILED;
    private DisplayMode routeDisplayMode = DisplayMode.BASIC;
    private DisplayMode runwayDisplayMode = DisplayMode.BASIC;

    /**
     * Construct a map
     */
    public AircraftMap() {
        super();
        this.setBackground(GraphicsTheme.Styles.MAP_BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setCenter(0, 0);
    }

    public void addNavAids(List<NavigationAid> aids) {
        for (NavigationAid aid : aids) {
            if (aid.getType().equals("VOR")) {
                Position pos = new Position();
                pos.setLatitude(aid.getLatitude());
                pos.setLongitude(aid.getLongitude());
                pos.setAltitude(aid.getElevation());
                this.navaids.add(new VORSymbol(aid.getIdent(), pos, aid.getFrequency()));
            }
            if (aid.getType().equals("VOR-DME")) {
                Position pos = new Position();
                pos.setLatitude(aid.getLatitude());
                pos.setLongitude(aid.getLongitude());
                pos.setAltitude(aid.getElevation());
                this.navaids.add(new VORDMESymbol(aid.getIdent(), pos, aid.getFrequency()));
            }
            if (aid.getType().equals("VORTAC")) {
                Position pos = new Position();
                pos.setLatitude(aid.getLatitude());
                pos.setLongitude(aid.getLongitude());
                pos.setAltitude(aid.getElevation());
                this.navaids.add(new VORTACSymbol(aid.getIdent(), pos, aid.getFrequency()));
            }
            if (aid.getType().equals("NDB")) {
                Position pos = new Position();
                pos.setLatitude(aid.getLatitude());
                pos.setLongitude(aid.getLongitude());
                pos.setAltitude(aid.getElevation());
                this.navaids.add(new NDBSymbol(aid.getIdent(), pos, aid.getFrequency()));
            }
        }
    }

    public void addAirports(List<Airport> airports) {
        airports.stream().filter(airport -> {
            return VALID_AIRPORT_TYPES.contains(airport.getType());
        }).forEach(airport -> {
            this.airports.add(new AirportSymbol(airport));
        });
    }

    public void addRoutes(RouteGraph routes) {
        routes.getAirways().forEach(airway -> this.routes.add(new RouteSymbol(airway)));
    }

    public void addRunways(List<Runway> runways) {
        runways.forEach(runway -> this.runways.add(new RunwaySymbol(runway)));
    }

    /**
     * Paint the Tabbable on a Graphics instance
     *
     * @param g the graphics context to paint on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Base layer -- Routes
        this.routes.forEach(route -> route.drawOn(g2d, this, this.routeDisplayMode));

        // Compass rose
        this.drawPositionAndScale(g2d);
        this.drawRange(g2d);

        this.runways.forEach(runway -> runway.drawOn(g2d, this, this.runwayDisplayMode));

        // Aids to Navigation
        this.navaids.forEach(aid -> aid.drawOn(g2d, this, this.navaidDisplayMode));

        // Airports
        this.airports.forEach(airport -> airport.drawOn(g2d, this, this.airportDisplayMode));

        // Planes
        this.planes.forEach(item -> item.drawOn(g2d, this, DisplayMode.DETAILED));

        g2d.dispose();
    }

    public void toggleNavAids() {
        this.navaidDisplayMode = this.navaidDisplayMode.next();
        this.redraw();
    }

    public void toggleAirports() {
        this.airportDisplayMode = this.airportDisplayMode.next();
        this.redraw();
    }

    public void toggleRoutes() {
        this.routeDisplayMode = this.routeDisplayMode.next();
        this.redraw();
    }

    public void toggleRunways() {
        this.runwayDisplayMode = this.runwayDisplayMode.next();
        this.redraw();
    }

    private void drawPositionAndScale(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        g.setFont(newFont);
        g.setColor(GraphicsTheme.Styles.MAP_LABEL_COLOR);
        g.drawString(String.format("%08.5f N", this.centerLatitude), TEXT_PADDING, (int) FONT_SIZE + TEXT_PADDING);
        g.drawString(String.format("%08.5f E", this.centerLongitude), TEXT_PADDING, (int) FONT_SIZE * 2 + TEXT_PADDING);
        g.drawString(String.format("%d nm", this.getRangeRadius()), TEXT_PADDING, (int) FONT_SIZE * 3 + TEXT_PADDING);
    }

    private int getRangeRadius() {
        double milesHigh = this.getHeight() / this.getPixelsPerNauticalMile();
        double milesWide = this.getWidth() / this.getPixelsPerNauticalMile();
        double screenMiles = Math.min(milesHigh, milesWide);
        int milesPerRing = (int) screenMiles / NUMBER_OF_RANGE_RINGS;
        return milesPerRing;
    }

    private List<Integer> getRangeRadii() {
        int rangeRadius = this.getRangeRadius();
        List<Integer> radii = new ArrayList<>();
        for (int ringNumber = 1; ringNumber <= NUMBER_OF_RANGE_RINGS; ringNumber++) {
            radii.add(rangeRadius * ringNumber);
        }
        return radii;
    }

    private void drawRange(Graphics g) {
        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2;
        g.setColor(GraphicsTheme.Styles.MAP_RANGE_COLOR);
        for (Integer radius : this.getRangeRadii()) {
            int pixelRadius = (int) (this.getPixelsPerNauticalMile() * radius);
            g.drawOval(centerX - pixelRadius, centerY - pixelRadius, pixelRadius * 2, pixelRadius * 2);
        }
        g.drawLine(centerX, 0, centerX, this.getHeight());
        g.drawLine(0, centerY, this.getWidth(), centerY);
    }

    public void setPlanes(List<Drawable> planes) {
        this.planes = planes;
        this.redraw();
    }

    public void redraw() {
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void setCenter(double latitude, double longitude) {
        this.centerLatitude = latitude;
        this.centerLongitude = longitude;
        this.redraw();
    }

    public double getCenterLatitude() {
        return this.centerLatitude;
    }

    public double getCenterLongitude() {
        return this.centerLongitude;
    }

    public double getPixelsPerDegreeLatitude() {
        return this.pixelsPerNauticalMile * NAUTICAL_MILES_PER_DEGREE_LATITUDE;
    }

    public double getPixelsPerDegreeLongitude() {
        return this.pixelsPerNauticalMile * this.getNauticalMilesPerDegreeLongitude();
    }

    public double getNauticalMilesPerDegreeLongitude() {
        double milesPerDegree = Math.abs(Math.cos(Math.toRadians(this.centerLatitude)) * NAUTICAL_MILES_PER_DEGREE_LATITUDE);
        return milesPerDegree;
    }

    public double getPixelsPerNauticalMile() {
        return this.pixelsPerNauticalMile;
    }

    public void zoomIn() {
        this.pixelsPerNauticalMile = Math.min(MAX_ZOOM_PIXELS_PER_MILE, this.pixelsPerNauticalMile * 2);
        this.redraw();
    }

    public void zoomOut() {
        this.pixelsPerNauticalMile = Math.max(MIN_ZOOM_PIXELS_PER_MILE, this.pixelsPerNauticalMile / 2);
        this.redraw();
    }

    public void moveEast() {
        double degreesToMove = PAN_INTERVAL_MILES / this.getNauticalMilesPerDegreeLongitude();
        this.centerLongitude = Math.min(this.centerLongitude + degreesToMove, MAX_LONGITUDE);
        this.redraw();
    }

    public void moveWest() {
        double degreesToMove = PAN_INTERVAL_MILES / this.getNauticalMilesPerDegreeLongitude();
        this.centerLongitude = Math.max(this.centerLongitude - degreesToMove, MIN_LONGITUDE);
        this.redraw();
    }

    public void moveNorth() {
        double degreesToMove = PAN_INTERVAL_MILES / NAUTICAL_MILES_PER_DEGREE_LATITUDE;
        this.centerLatitude = Math.min(this.centerLatitude + degreesToMove, MAX_LATITUDE);
        this.redraw();
    }

    public void moveSouth() {
        double degreesToMove = PAN_INTERVAL_MILES / NAUTICAL_MILES_PER_DEGREE_LATITUDE;
        this.centerLatitude = Math.max(this.centerLatitude - degreesToMove, MIN_LATITUDE);
        this.redraw();
    }

}
