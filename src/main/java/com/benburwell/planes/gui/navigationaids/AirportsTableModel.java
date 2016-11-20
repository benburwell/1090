package com.benburwell.planes.gui.navigationaids;

import com.benburwell.planes.data.Airport;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author ben
 */
public class AirportsTableModel extends AbstractTableModel {
    public final String[] COLUMN_HEADERS = {"Identifier", "Name", "Country", "Municipality", "Scheduled Service", "IATA Code", "Local Code"};

    private List<Airport> airports;

    public AirportsTableModel(List<Airport> airports) {
        this.airports = airports;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public int getRowCount() {
        return airports.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_HEADERS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Airport airport = this.airports.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return airport.getIdent();
            case 1:
                return airport.getName();
            case 2:
                return airport.getIsoCountry();
            case 3:
                return airport.getMunicipality();
            case 4:
                return airport.isScheduledService();
            case 5:
                return airport.getIataCode();
            case 6:
                return airport.getLocalCode();
        }
        return null;
    }
}
