package com.benburwell.planes.gui.navigationaids;

import com.benburwell.planes.data.CSVObjectStore;
import com.benburwell.planes.data.NavigationAid;
import com.benburwell.planes.gui.Tabbable;
import com.benburwell.planes.gui.airportstable.NavigationAidsTableModel;

import javax.swing.*;

/**
 * Created by ben on 11/19/16.
 */
public class NavigationAidComponent implements Tabbable {
    private JTable table;
    private NavigationAidsTableModel tableModel;
    private JScrollPane scrollPane;

    public NavigationAidComponent(CSVObjectStore<NavigationAid> store) {
        this.tableModel = new NavigationAidsTableModel(store);
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
        return "Navigation Aids";
    }
}
