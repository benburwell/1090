package com.benburwell.planes.gui.aircraftmap;

import com.benburwell.planes.data.*;
import com.benburwell.planes.graph.RouteGraph;
import com.benburwell.planes.gui.Tabbable;
import com.benburwell.planes.gui.aircraftmap.symbols.PlaneSymbol;

import java.awt.KeyboardFocusManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.util.stream.Collectors;

/**
 * @author ben
 */
public class AircraftMapComponent implements Tabbable {
    public final long PLANE_EXPIRY_MILLIS = 60 * 1000;
    public final int MAX_REFRESH_MILLIS = 5 * 1000;

    private AircraftStore store;
    private AircraftMap mapPanel;
    private AircraftStoreListener aircraftStoreListener;

    public AircraftMapComponent(AircraftStore store, CSVObjectStore<NavigationAid> navaids, CSVObjectStore<Airport> airportStore, RouteGraph routeGraph, CSVObjectStore<Runway> runwayStore) {
        this.store = store;

        this.setupMap();
        this.bindKeys();
        this.setupListener();

        this.store.subscribe(this.aircraftStoreListener);
        this.mapPanel.addNavAids(navaids.getObjects());
        this.mapPanel.addAirports(airportStore.getObjects());
        this.mapPanel.addRoutes(routeGraph);
        this.mapPanel.addRunways(runwayStore.getObjects().stream().filter(Runway::isDrawable).collect(Collectors.toList()));

        final Timer t = new Timer(MAX_REFRESH_MILLIS, e -> {
            AircraftMapComponent.this.aircraftStoreListener.aircraftStoreChanged();
        });
        t.start();
    }

    private void setupMap() {
        this.mapPanel = new AircraftMap();
        this.mapPanel.setCenter(40.6188942, -75.4947205);
    }

    /**
     * Handle keyboard shortcuts
     *
     * Key  Function
     * ===  ========
     *  h   pan west
     *  j   pan south
     *  k   pan north
     *  l   pan east
     *  +   zoom in
     *  -   zoom out
     *  0   reset lat/lon to home
     *
     *  n   toggle navaids
     *  v   toggle routes
     *  f   toggle airfields
     *  r   toggle runways
     */
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
            } else if (e.getKeyCode() == KeyEvent.VK_N && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.toggleNavAids();
            } else if (e.getKeyCode() == KeyEvent.VK_F && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.toggleAirports();
            } else if (e.getKeyCode() == KeyEvent.VK_V && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.toggleRoutes();
            } else if (e.getKeyCode() == KeyEvent.VK_R && e.getID() == KeyEvent.KEY_PRESSED) {
                this.mapPanel.toggleRunways();
            }
            return false;
        });
    }

    private void setupListener() {
        this.aircraftStoreListener = new AircraftStoreListener() {
            @Override
            public void aircraftStoreChanged() {
                Date minTime = new Date(System.currentTimeMillis() - PLANE_EXPIRY_MILLIS);
                List<Drawable> planes = new ArrayList<>();
                store.getAircraft().values().forEach(aircraft -> {
                    if (aircraft.getCurrentPosition().getTimestamp().after(minTime)) {
                        planes.add(new PlaneSymbol(aircraft));
                    }
                });
                mapPanel.setPlanes(planes);
                mapPanel.validate();
                mapPanel.repaint();
            }

            @Override
            public boolean respondTo(String aircraftId) {
                return true;
            }
        };
    }

    @Override
    public JComponent getComponent() {
        return this.mapPanel;
    }

    @Override
    public String getName() {
        return "Map";
    }
}
