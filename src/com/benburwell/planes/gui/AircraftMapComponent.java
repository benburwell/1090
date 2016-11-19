package com.benburwell.planes.gui;

import com.benburwell.planes.data.AircraftStore;
import com.benburwell.planes.data.AircraftStoreListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by ben on 11/18/16.
 */
public class AircraftMapComponent implements ViewComponent {
    private AircraftStore store;
    private AircraftMap mapPanel;

    public AircraftMapComponent(AircraftStore store) {
        this.store = store;
        this.mapPanel = new AircraftMap();
        this.mapPanel.setCenter(40.6188942, -75.4947205);
        this.store.subscribe(new AircraftStoreListener() {
            @Override
            public void aircraftStoreChanged() {
                List<Drawable> planes = new ArrayList<>();
                store.getAircraft().values().forEach(aircraft -> {
                    String name = !aircraft.getCallsign().isEmpty() ? aircraft.getCallsign() : aircraft.getHexIdent();
                    double lat = aircraft.getCurrentPosition().getLatitude();
                    double lon = aircraft.getCurrentPosition().getLongitude();
                    planes.add(new Plane(name, lat, lon));
                });
                mapPanel.setPlanes(planes);
                mapPanel.validate();
                mapPanel.repaint();
            }

            @Override
            public boolean respondTo(String aircraftId) {
                return true;
            }
        });
    }

    @Override
    public JComponent getComponent() {
        return this.mapPanel;
    }

    private class AircraftMap extends JPanel {
        private final float FONT_SIZE = 12;
        private final int TEXT_PADDING = 5;
        private List<Drawable> planes = new ArrayList<>();
        private double centerLatitude;
        private double centerLongitude;

        public AircraftMap() {
            super();
            this.setBackground(Color.black);
            this.setBorder(BorderFactory.createEmptyBorder());
            this.setCenter(0, 0);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.planes.forEach(item -> item.drawOn(g, this));
            this.drawPositionAndScale(g);
        }

        public void drawPositionAndScale(Graphics g) {
            Font currentFont = g.getFont();
            Font newFont = currentFont.deriveFont(FONT_SIZE);
            g.setFont(newFont);
            g.setColor(Color.gray);
            g.drawString(String.format("%08.5f N", this.centerLatitude), TEXT_PADDING, (int)FONT_SIZE + TEXT_PADDING);
            g.drawString(String.format("%08.5f E", this.centerLongitude), TEXT_PADDING, (int)FONT_SIZE * 2 + TEXT_PADDING);
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

        public double getPixelsPerDegree() {
            return 600;
        }
    }

    private interface Drawable {
        void drawOn(Graphics graphicsContext, AircraftMap map);
    }

    private class GeoPoint {
        private double latitude;
        private double longitude;

        public GeoPoint(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public int getX(AircraftMap map) {
            double degreesFromCenter = map.getCenterLongitude() - this.longitude;
            double pixelsFromCenter = degreesFromCenter * map.getPixelsPerDegree();
            double centerPixels = map.getSize().getWidth() / 2;
            int xPosition = (int)(centerPixels - pixelsFromCenter);

            System.out.println("Degrees from center: " + degreesFromCenter + "; Pixels from center: " + pixelsFromCenter);
            return xPosition;
        }

        public int getY(AircraftMap map) {
            double degreesFromCenter = map.getCenterLatitude() - this.latitude;
            double pixelsFromCenter = degreesFromCenter * map.getPixelsPerDegree();
            double centerPixels = map.getSize().getHeight() / 2;
            int yPosition = (int)(centerPixels + pixelsFromCenter);
            return yPosition;
        }
    }

    private class Plane extends GeoPoint implements Drawable {
        private String name;
        private final int DOT_DIMENSION = 4;
        private final int TEXT_OFFSET_X = 10;
        private final int TEXT_OFFSET_Y = 15;

        public Plane(String name, double latitude, double longitude) {
            super(latitude, longitude);
            this.name = name;
        }

        public void drawOn(Graphics g, AircraftMap map) {
            g.setColor(new Color(200, 90, 0));
            g.fillRect(this.getX(map) - (DOT_DIMENSION / 2), this.getY(map) - (DOT_DIMENSION / 2), DOT_DIMENSION, DOT_DIMENSION);
            g.drawString(this.name, this.getX(map) + TEXT_OFFSET_X, this.getY(map) + TEXT_OFFSET_Y);
        }
    }
}
