package com.benburwell.planes.data;

/**
 * @author ben
 */
public interface AircraftStoreListener {
    void aircraftStoreChanged();
    boolean respondTo(String aircraftId);
}
