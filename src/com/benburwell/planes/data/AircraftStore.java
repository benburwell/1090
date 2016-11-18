package com.benburwell.planes.data;

import com.benburwell.planes.sbs.SBSPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by ben on 11/17/16.
 */
public class AircraftStore {
    private Map<String,Aircraft> aircraftMap = new HashMap<>();
    private List<AircraftStoreListener> aircraftListeners = new ArrayList<>();

    public Map<String,Aircraft> getAircraft() {
        return this.aircraftMap;
    }

    public void addPacket(SBSPacket packet) {
        if (!this.aircraftMap.containsKey(packet.getHexIdent())) {
            this.aircraftMap.put(packet.getHexIdent(), new Aircraft(packet.getHexIdent()));
        }
        this.aircraftMap.get(packet.getHexIdent()).handleUpdate(packet);
        this.aircraftListeners.stream()
                .filter(listener -> listener.respondTo(packet.getHexIdent()))
                .forEach(AircraftStoreListener::aircraftStoreChanged);
    }

    public void subscribe(AircraftStoreListener listener) {
        this.aircraftListeners.add(listener);
    }
}
