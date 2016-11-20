package com.benburwell.planes.gui;

import com.benburwell.planes.data.NavigationAidStore;

import javax.swing.*;

/**
 * Created by ben on 11/19/16.
 */
public class NavigationAidComponent implements ViewComponent {
    private JTable table;
    private NavigationAidsTableModel tableModel;
    private JScrollPane scrollPane;

    public NavigationAidComponent(NavigationAidStore store) {
        this.tableModel = new NavigationAidsTableModel(store);
        this.table = new JTable(this.tableModel);
        this.table.setFillsViewportHeight(true);
        this.scrollPane = new JScrollPane(table);
    }

    @Override
    public JComponent getComponent() {
        return this.scrollPane;
    }
}
