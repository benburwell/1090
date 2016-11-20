package com.benburwell.planes.gui.airportstable;

import com.benburwell.planes.data.Airport;
import com.benburwell.planes.data.CSVObjectStore;
import com.benburwell.planes.gui.Tabbable;
import com.benburwell.planes.gui.navigationaids.AirportsTableModel;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author ben
 */
public class AirportsComponent implements Tabbable {
    private JScrollPane scrollPane = new JScrollPane();

    public AirportsComponent(CSVObjectStore<Airport> airportStore) {
        AirportsTableModel tableModel = new AirportsTableModel(airportStore.getObjects());
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
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
