package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.data.AircraftStore;
import com.benburwell.planes.data.AircraftStoreListener;
import com.benburwell.planes.data.Position;
import com.benburwell.planes.gui.ViewComponent;

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

        List<Drawable> planes = new ArrayList<>();
        planes.add(new Plane("JBU1111", 40.6188942, -75.4947205, 36000));
        planes.add(new Plane("JBU1112", 40.6178942, -75.4347205, 42000));
        planes.add(new Plane("JBU1114", 40.5178942, -75.5347205, 3500));
        planes.add(new Plane("JBU1115", 40.3178942, -75.1347205, 0));
        this.mapPanel.setPlanes(planes);
    }

    public void focusNextAircraft() {
    //    List<String> aircraftIdentifiers = new ArrayList<>(this.store.getAircraft().keySet());
    //    Collections.sort(aircraftIdentifiers);
    //    if (this.focusedAircraftIdentifier == null && aircraftIdentifiers.size() > 0) {
    //        this.focusedAircraftIdentifier = aircraftIdentifiers.get(0);
    //    } else {
    //        int idx = aircraftIdentifiers.indexOf(this.focusedAircraftIdentifier);
    //        if (idx > 0 && idx < aircraftIdentifiers.size() - 1) {
    //            this.focusedAircraftIdentifier = aircraftIdentifiers.get(idx++);
    //        } else if (aircraftIdentifiers.size() > 0) {
    //            this.focusedAircraftIdentifier = aircraftIdentifiers.get(0);
    //        } else {
    //            this.focusedAircraftIdentifier = null;
    //        }
    //    }
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
                System.out.println("Zooming out");
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
                    planes.add(new Plane(name, aircraft.getCurrentPosition()));
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

}
