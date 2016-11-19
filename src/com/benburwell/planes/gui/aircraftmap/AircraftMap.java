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

    // map manipulation constants
    public final int ZOOM_INTERVAL_MILES = 10;
    public final double PAN_INTERVAL_MILES = 1.0;
    public final int RING_SPACING = 10;

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
        this.planes.forEach(item -> item.drawOn(g, this));
        this.drawPositionAndScale(g);
        this.drawRange(g);
    }

    /**
     *
     * @param g
     */
    public void drawPositionAndScale(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        g.setFont(newFont);
        g.setColor(GraphicsTheme.Colors.BLUE);
        g.drawString(String.format("%08.5f N", this.centerLatitude), TEXT_PADDING, (int) FONT_SIZE + TEXT_PADDING);
        g.drawString(String.format("%08.5f E", this.centerLongitude), TEXT_PADDING, (int) FONT_SIZE * 2 + TEXT_PADDING);
        g.drawString("1 nm", TEXT_PADDING, (int) FONT_SIZE * 3 + TEXT_PADDING);
        g.drawLine(TEXT_PADDING, (int) FONT_SIZE * 3 + TEXT_PADDING, (int) (TEXT_PADDING + this.getPixelsPerNauticalMile()), (int) FONT_SIZE * 3 + TEXT_PADDING);
    }

    public void drawRange(Graphics g) {
        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2;
        g.setColor(GraphicsTheme.Colors.BASE_3);
        int diameter = (int) this.getPixelsPerNauticalMile() * RING_SPACING * 2;
        int ringNumber = 1;
        while (diameter < this.getWidth() || diameter < this.getHeight()) {
            g.drawOval(centerX - (diameter / 2), centerY - (diameter / 2), diameter, diameter);
            g.drawString(String.format("%d", ringNumber * RING_SPACING), centerX + (diameter / 2) + TEXT_PADDING, (int) (centerY + FONT_SIZE + TEXT_PADDING));
            g.drawString(String.format("%d", ringNumber * RING_SPACING), centerX + TEXT_PADDING, centerY - (int) ((diameter / 2) + FONT_SIZE));
            diameter += this.getPixelsPerNauticalMile() * RING_SPACING * 2;
            ringNumber++;
        }
        g.drawLine(centerX, 0, centerX, this.getHeight());
        g.drawLine(0, centerY, this.getWidth(), centerY);
    }

    public void setPlanes(List<Drawable> planes) {
        this.planes = planes;
        this.invalidate();
    }

    public void setCenter(double latitude, double longitude) {
        this.centerLatitude = latitude;
        this.centerLongitude = longitude;
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
        System.out.println("Miles per degree at " + this.centerLatitude + " N: " + milesPerDegree);
        return milesPerDegree;
    }

    public double getPixelsPerNauticalMile() {
        return this.pixelsPerNauticalMile;
    }

    public void zoomIn() {
        this.pixelsPerNauticalMile += ZOOM_INTERVAL_MILES;
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void zoomOut() {
        this.pixelsPerNauticalMile -= ZOOM_INTERVAL_MILES;
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveEast() {
        double degreesToMove = PAN_INTERVAL_MILES / this.getNauticalMilesPerDegreeLongitude();
        this.centerLongitude = Math.min(this.centerLongitude + degreesToMove, MAX_LONGITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveWest() {
        double degreesToMove = PAN_INTERVAL_MILES / this.getNauticalMilesPerDegreeLongitude();
        this.centerLongitude = Math.max(this.centerLongitude - degreesToMove, MIN_LONGITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveNorth() {
        double degreesToMove = PAN_INTERVAL_MILES / NAUTICAL_MILES_PER_DEGREE_LATITUDE;
        this.centerLatitude = Math.min(this.centerLatitude + degreesToMove, MAX_LATITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveSouth() {
        double degreesToMove = PAN_INTERVAL_MILES / NAUTICAL_MILES_PER_DEGREE_LATITUDE;
        this.centerLatitude = Math.max(this.centerLatitude - degreesToMove, MIN_LATITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }
}
