package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.gui.GraphicsTheme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 11/19/16.
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

    // instance fields
    private List<Drawable> planes = new ArrayList<>();
    private double centerLatitude;
    private double centerLongitude;
    private int pixelsPerNauticalMile = 10;

    /**
     * Construct a map
     */
    public AircraftMap() {
        super();
        this.setBackground(GraphicsTheme.Colors.BASE_1);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setCenter(0, 0);
    }

    /**
     * Paint the ViewComponent on a Graphics instance
     *
     * @param g the graphics context to paint on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawPositionAndScale(g);
        this.drawRange(g);
        this.planes.forEach(item -> item.drawOn(g, this));
    }

    public void drawPositionAndScale(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        g.setFont(newFont);
        g.setColor(GraphicsTheme.Colors.BLUE);
        g.drawString(String.format("%08.5f N", this.centerLatitude), TEXT_PADDING, (int) FONT_SIZE + TEXT_PADDING);
        g.drawString(String.format("%08.5f E", this.centerLongitude), TEXT_PADDING, (int) FONT_SIZE * 2 + TEXT_PADDING);
        g.drawString(String.format("%d nm", this.getRangeRadius()), TEXT_PADDING, (int) FONT_SIZE * 3 + TEXT_PADDING);
    }

    public int getRangeRadius() {
        double milesHigh = this.getHeight() / this.getPixelsPerNauticalMile();
        double milesWide = this.getWidth() / this.getPixelsPerNauticalMile();
        double screenMiles = Math.min(milesHigh, milesWide);
        int milesPerRing = (int) screenMiles / NUMBER_OF_RANGE_RINGS;
        return milesPerRing;
    }

    public List<Integer> getRangeRadii() {
        int rangeRadius = this.getRangeRadius();
        List<Integer> radii = new ArrayList<>();
        for (int ringNumber = 1; ringNumber <= NUMBER_OF_RANGE_RINGS; ringNumber++) {
            radii.add(rangeRadius * ringNumber);
        }
        return radii;
    }

    public void drawRange(Graphics g) {
        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2;
        g.setColor(GraphicsTheme.Colors.BASE_3);
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