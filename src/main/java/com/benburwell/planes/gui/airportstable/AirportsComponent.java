package com.benburwell.planes.gui.airportstable;

import com.benburwell.planes.data.Airport;
import com.benburwell.planes.data.CSVObjectStore;
import com.benburwell.planes.gui.Tabbable;
import com.benburwell.planes.gui.navigationaids.AirportsTableModel;

import javax.swing.*;

/**
 * Created by ben on 11/19/16.
 */
public class AirportsComponent implements Tabbable {
    private JScrollPane scrollPane = new JScrollPane();
    private JTable table = new JTable();
    private AirportsTableModel tableModel;

    public AirportsComponent(CSVObjectStore<Airport> airportStore) {
        this.tableModel = new AirportsTableModel(airportStore.getObjects());
        this.table = new JTable(this.tableModel);
        this.table.setFillsViewportHeight(true);
        this.scrollPane = new JScrollPane(table);
    }

    @Override
    public JComponent getComponent() {
        return this.scrollPane;
    }

    @Override
    public String getName() {
        return "Airports";
    }
}
