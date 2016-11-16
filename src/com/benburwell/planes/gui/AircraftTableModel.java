package com.benburwell.planes.gui;

import com.benburwell.planes.data.Aircraft;

import javax.swing.table.AbstractTableModel;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ben on 11/15/16.
 */
public class AircraftTableModel extends AbstractTableModel {
    private Map<String,Aircraft> aircraftMap;
    private String[] columnNames = { "Hex", "Callsign", "Squawk", "Latitude", "Longitude", "Altitude", "Packets" };

    public AircraftTableModel(Map<String,Aircraft> aircraftMap) {
        this.aircraftMap = aircraftMap;
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
                return aircraft.getSquawk();
            case 3:
                return aircraft.getCurrentPosition().getLatitude();
            case 4:
                return aircraft.getCurrentPosition().getLongitude();
            case 5:
                return aircraft.getCurrentPosition().getAltitude();
            case 6:
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