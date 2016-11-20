package com.benburwell.planes.gui.aircrafttable;

import com.benburwell.planes.data.Aircraft;
import com.benburwell.planes.data.AircraftStore;
import com.benburwell.planes.data.AircraftStoreListener;

import javax.swing.table.AbstractTableModel;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author ben
 */
public class AircraftTableModel extends AbstractTableModel {
    private Map<String,Aircraft> aircraftMap;
    private String[] columnNames = { "Hex", "Callsign", "Latitude", "Longitude", "Altitude", "Vertical Rate", "Track", "Ground Speed", "Packets" };

    public AircraftTableModel(AircraftStore store) {
        this.aircraftMap = store.getAircraft();
        store.subscribe(new AircraftStoreListener() {
            @Override
            public void aircraftStoreChanged() {
                AircraftTableModel.super.fireTableDataChanged();
            }

            @Override
            public boolean respondTo(String aircraftId) {
                // listen for all changes
                return true;
            }
        });
    }

    @Override
    public int getRowCount() {
        return this.aircraftMap.keySet().size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return this.columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Aircraft> aircraftList = this.getAircraftList();
        Aircraft aircraft = aircraftList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return aircraft.getHexIdent();
            case 1:
                return aircraft.getCallsign();
            case 2:
                return aircraft.getCurrentPosition().getLatitude();
            case 3:
                return aircraft.getCurrentPosition().getLongitude();
            case 4:
                return aircraft.getCurrentPosition().getAltitude();
            case 5:
                return aircraft.getVerticalRate();
            case 6:
                return aircraft.getTrack();
            case 7:
                return aircraft.getGroundSpeed();
            case 8:
                return aircraft.getPacketCount();
        }
        return "";
    }

    private List<Aircraft> getAircraftList() {
        List<Aircraft> aircraftList = new ArrayList<>(this.aircraftMap.values());
        Collections.sort(aircraftList);
        return aircraftList;
    }
}
