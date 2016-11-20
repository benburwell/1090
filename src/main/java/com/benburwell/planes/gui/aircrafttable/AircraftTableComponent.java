package com.benburwell.planes.gui.aircrafttable;

import com.benburwell.planes.data.AircraftStore;
import com.benburwell.planes.gui.Tabbable;

import javax.swing.*;

/**
 * Created by ben on 11/17/16.
 */
public class AircraftTableComponent implements Tabbable {
    private JTable table;
    private AircraftTableModel tableModel;
    private JScrollPane scrollPane;

    public AircraftTableComponent(AircraftStore store) {
        this.tableModel = new AircraftTableModel(store);
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
        return "Aircraft";
    }
}
