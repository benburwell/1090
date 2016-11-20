package com.benburwell.planes.gui.aircrafttable;

import com.benburwell.planes.data.AircraftStore;
import com.benburwell.planes.gui.Tabbable;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author ben
 */
public class AircraftTableComponent implements Tabbable {
    private JScrollPane scrollPane;

    public AircraftTableComponent(AircraftStore store) {
        AircraftTableModel tableModel = new AircraftTableModel(store);
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
        return "Aircraft";
    }
}
