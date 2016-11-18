package com.benburwell.planes.data;

/**
 * Created by ben on 11/17/16.
 */
public interface AircraftStoreListener {
    void aircraftStoreChanged();
    boolean respondTo(String aircraftId);
}
