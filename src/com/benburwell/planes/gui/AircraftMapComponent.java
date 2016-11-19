package com.benburwell.planes.gui;

import com.benburwell.planes.data.AircraftStore;
import com.benburwell.planes.data.AircraftStoreListener;
import com.benburwell.planes.data.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ben on 11/18/16.
 */
public class AircraftMapComponent implements ViewComponent {
    private AircraftStore store;
    private AircraftMap mapPanel;
    private String focusedAircraftIdentifier = null;

    public AircraftMapComponent(AircraftStore store) {
        this.store = store;
        this.setupMap();
        this.bindKeys();
        this.subscribeToChanges();
    }

    public void focusNextAircraft() {
        List<String> aircraftIdentifiers = new ArrayList<>(this.store.getAircraft().keySet());
        Collections.sort(aircraftIdentifiers);
        if (this.focusedAircraftIdentifier == null && aircraftIdentifiers.size() > 0) {
            this.focusedAircraftIdentifier = aircraftIdentifiers.get(0);
        } else {
            int idx = aircraftIdentifiers.indexOf(this.focusedAircraftIdentifier);
            if (idx > 0 && idx < aircraftIdentifiers.size() - 1) {
                this.focusedAircraftIdentifier = aircraftIdentifiers.get(idx++);
            } else if (aircraftIdentifiers.size() > 0) {
                this.focusedAircraftIdentifier = aircraftIdentifiers.get(0);
            } else {
                this.focusedAircraftIdentifier = null;
            }
        }
    }

    private void setupMap() {
        this.mapPanel = new AircraftMap();
        this.mapPanel.setCenter(40.6188942, -75.4947205);
    }

    private void bindKeys() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getKeyCode() == KeyEvent.VK_EQUALS && e.isShiftDown() && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.zoomIn();
            } else if (e.getKeyCode() == KeyEvent.VK_MINUS && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.zoomOut();
            } else if (e.getKeyCode() == KeyEvent.VK_L && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.moveEast();
            } else if (e.getKeyCode() == KeyEvent.VK_H && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.moveWest();
            } else if (e.getKeyCode() == KeyEvent.VK_J && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.moveSouth();
            } else if (e.getKeyCode() == KeyEvent.VK_K && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.moveNorth();
            } else if (e.getKeyCode() == KeyEvent.VK_0 && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.setCenter(40.6188942, -75.4947205);
            } else if (e.getKeyCode() == KeyEvent.VK_TAB && e.getID() == KeyEvent.KEY_PRESSED) {
                this.focusNextAircraft();
                this.centerMapOnPlane(this.focusedAircraftIdentifier);
            }
            return false;
        });
    }

    private void centerMapOnPlane(String identifier) {
        if (identifier != null) {
            Position pos = this.store.getAircraft().get(identifier).getCurrentPosition();
            this.mapPanel.setCenter(pos.getLatitude(), pos.getLongitude());
        }
    }

    private void subscribeToChanges() {
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
        private final int ZOOM_INTERVAL = 100;
        private final double PAN_INTERVAL = 1.0 / 60.0;
        private final double MAX_LATITUDE = 90.0;
        private final double MIN_LATITUDE = -90.0;
        private final double MAX_LONGITUDE = 180.0;
        private final double MIN_LONGITUDE = -180.0;
        private final int RING_SPACING = 10;
        private List<Drawable> planes = new ArrayList<>();
        private double centerLatitude;
        private double centerLongitude;
        private int pixelsPerDegree = 600;

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
            g.drawString(String.format("%08.5f N", this.centerLatitude), TEXT_PADDING, (int)FONT_SIZE + TEXT_PADDING);
            g.drawString(String.format("%08.5f E", this.centerLongitude), TEXT_PADDING, (int)FONT_SIZE * 2 + TEXT_PADDING);
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

        public double getPixelsPerDegree() {
            return this.pixelsPerDegree;
        }

        public double getPixelsPerNauticalMile() {
            return this.pixelsPerDegree / 60.0;
        }

        public void zoomIn() {
            this.pixelsPerDegree += ZOOM_INTERVAL;
            this.invalidate();
            this.validate();
            this.repaint();
        }

        public void zoomOut() {
            this.pixelsPerDegree -= ZOOM_INTERVAL;
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
        private final int DOT_DIMENSION = 10;
        private final int TEXT_OFFSET_X = 10;
        private final int TEXT_OFFSET_Y = 15;

        public Plane(String name, double latitude, double longitude) {
            super(latitude, longitude);
            this.name = name;
        }

        public void drawOn(Graphics g, AircraftMap map) {
            int x = this.getX(map);
            int y = this.getY(map);

            // draw the plane dot
            g.setColor(GraphicsTheme.Colors.ORANGE);
            g.fillOval(x - (DOT_DIMENSION / 2), y - (DOT_DIMENSION / 2), DOT_DIMENSION, DOT_DIMENSION);

            // draw the name of the plane
            g.setColor(GraphicsTheme.Colors.BLUE);
            g.drawString(this.name, this.getX(map) + TEXT_OFFSET_X, this.getY(map) + TEXT_OFFSET_Y);
        }
    }
}
