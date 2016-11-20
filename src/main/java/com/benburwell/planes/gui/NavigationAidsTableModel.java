package com.benburwell.planes.gui;

import com.benburwell.planes.data.NavigationAid;
import com.benburwell.planes.data.NavigationAidStore;

import javax.swing.table.AbstractTableModel;

/**
 * Created by ben on 11/19/16.
 */
public class NavigationAidsTableModel extends AbstractTableModel {
    private final String[] COLUMN_NAMES = { "Ident", "Type", "Frequency", "DME Frequency", "DME Channel", "Usage Type", "Power", "Airport" };
    private NavigationAidStore store;

    public NavigationAidsTableModel(NavigationAidStore store) {
        this.store = store;
    }

    @Override
    public int getRowCount() {
        return this.store.getNavigationAids().size();
    }

    @Override
    public String getColumnName(int col) {
        return COLUMN_NAMES[col];
    }

    @Override
    public int getColumnCount() {
        return this.COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NavigationAid aid = this.store.getNavigationAids().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return aid.getIdent();
            case 1:
                return aid.getType();
            case 2:
                return aid.getFrequency();
            case 3:
                return aid.getDmeFrequency();
            case 4:
                return aid.getDmeChannel();
            case 5:
                return aid.getUsageType();
            case 6:
                return aid.getPower();
            case 7:
                return aid.getAssociatedAirport();
        }
        return null;
    }
}
