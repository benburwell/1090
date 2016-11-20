package com.benburwell.planes.gui.navigationaids;

import com.benburwell.planes.data.CSVObjectStore;
import com.benburwell.planes.data.NavigationAid;
import com.benburwell.planes.gui.Tabbable;
import com.benburwell.planes.gui.airportstable.NavigationAidsTableModel;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author ben
 */
public class NavigationAidComponent implements Tabbable {
    private JScrollPane scrollPane;

    public NavigationAidComponent(CSVObjectStore<NavigationAid> store) {
        NavigationAidsTableModel tableModel = new NavigationAidsTableModel(store);
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
        return "Navigation Aids";
    }
}
