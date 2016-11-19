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
    private final double MAX_LATITUDE = 90.0;
    private final double MIN_LATITUDE = -90.0;
    private final double MAX_LONGITUDE = 180.0;
    private final double MIN_LONGITUDE = -180.0;
    private final double NAUTICAL_MILES_PER_DEGREE_LATITUDE = 60.0;

    private final float FONT_SIZE = 12;
    private final int TEXT_PADDING = 5;
    private final int ZOOM_INTERVAL = 10;
    private final double PAN_INTERVAL = 1.0 / 60.0;
    private final int RING_SPACING = 10;
    private List<Drawable> planes = new ArrayList<>();
    private double centerLatitude;
    private double centerLongitude;
    private int pixelsPerNauticalMile = 10;

    public AircraftMap() {
        super();
        this.setBackground(GraphicsTheme.Colors.BASE_1);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setCenter(0, 0);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.planes.forEach(item -> item.drawOn(g, this));
        this.drawPositionAndScale(g);
        this.drawRange(g);
    }

    public void drawPositionAndScale(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(FONT_SIZE);
        g.setFont(newFont);
        g.setColor(GraphicsTheme.Colors.BLUE);
        g.drawString(String.format("%08.5f N", this.centerLatitude), TEXT_PADDING, (int) FONT_SIZE + TEXT_PADDING);
        g.drawString(String.format("%08.5f E", this.centerLongitude), TEXT_PADDING, (int) FONT_SIZE * 2 + TEXT_PADDING);
    }

    public void drawRange(Graphics g) {
        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2;
        g.setColor(GraphicsTheme.Colors.BASE_3);
        int diameter = (int) this.getPixelsPerNauticalMile() * RING_SPACING;
        int ringNumber = 1;
        while (diameter < this.getWidth() || diameter < this.getHeight()) {
            g.drawOval(centerX - (diameter / 2), centerY - (diameter / 2), diameter, diameter);
            g.drawString(String.format("%d", ringNumber * RING_SPACING), centerX + (diameter / 2) + TEXT_PADDING, (int) (centerY + FONT_SIZE + TEXT_PADDING));
            g.drawString(String.format("%d", ringNumber * RING_SPACING), centerX + TEXT_PADDING, centerY - (int) ((diameter / 2) + FONT_SIZE));
            diameter += this.getPixelsPerNauticalMile() * 10;
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
        double milesPerDegree = Math.abs(Math.cos(this.centerLatitude) * NAUTICAL_MILES_PER_DEGREE_LATITUDE);
        return this.pixelsPerNauticalMile * milesPerDegree;
    }

    public double getPixelsPerNauticalMile() {
        return this.pixelsPerNauticalMile;
    }

    public void zoomIn() {
        this.pixelsPerNauticalMile += ZOOM_INTERVAL;
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void zoomOut() {
        this.pixelsPerNauticalMile -= ZOOM_INTERVAL;
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveEast() {
        this.centerLongitude = Math.min(this.centerLongitude + PAN_INTERVAL, MAX_LONGITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveWest() {
        this.centerLongitude = Math.max(this.centerLongitude - PAN_INTERVAL, MIN_LONGITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveNorth() {
        this.centerLatitude = Math.min(this.centerLatitude + PAN_INTERVAL, MAX_LATITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void moveSouth() {
        this.centerLatitude = Math.max(this.centerLatitude - PAN_INTERVAL, MIN_LATITUDE);
        this.invalidate();
        this.validate();
        this.repaint();
    }
}
