package com.benburwell.planes.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by ben on 11/17/16.
 */
public class MenuBarProvider {
    private JMenuBar menubar = new JMenuBar();
    private JMenu file = new JMenu("1090");
    private JMenuItem fileQuitMenuItem = new JMenuItem("Quit");
    private JMenu data = new JMenu("Data Source");
    private JMenuItem dataConnectItem = new JMenuItem("Connect to Remote...");
    private JMenuItem dataDisconnectItem = new JMenuItem("Disconnect");

    public MenuBarProvider() {
        fileQuitMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        file.add(fileQuitMenuItem);
        menubar.add(file);

        dataDisconnectItem.setEnabled(false);
        data.add(dataConnectItem);
        data.add(dataDisconnectItem);
        menubar.add(data);
    }

    public JMenuBar getMenuBar() {
        return this.menubar;
    }

    public JMenuItem getDataConnectItem() {
        return this.dataConnectItem;
    }

    public JMenuItem getDataDisconnectItem() {
        return this.dataDisconnectItem;
    }
}
