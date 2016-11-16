/**
 * Created by ben on 11/15/16.
 */

package com.benburwell.planes.gui;

import com.benburwell.planes.sbs.*;
import com.benburwell.planes.data.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.HashMap;

public class Main1090 extends JFrame {
    private AggregateDataSource sbsDataSource = new AggregateDataSource();
    private Map<String,Aircraft> aircraftMap = new HashMap<>();
    private int currentTcpConnection = 0;
    private AircraftTableModel aircraftTableModel = new AircraftTableModel(this.aircraftMap);

    public Main1090() {
        this.initUI();
    }

    private void initUI() {
        this.createMenuBar();

        this.createTable();

        this.setTitle("1090");
        this.setSize(100, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.openDataSource();
    }

    private void createTable() {
        JTable table = new JTable(this.aircraftTableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("1090");
        JMenuItem eMenuItem = new JMenuItem("Quit");
        JMenu data = new JMenu("Data");
        JMenuItem dataConnectItem = new JMenuItem("Connect...");
        JMenuItem dataDisconnectItem = new JMenuItem("Disconnect");

        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        file.add(eMenuItem);
        menubar.add(file);

        dataConnectItem.addActionListener((ActionEvent event) -> {
            if (this.currentTcpConnection == 0) {
                this.currentTcpConnection = this.addTcpSource("10.0.0.111", 30003);
                dataConnectItem.setEnabled(false);
                dataDisconnectItem.setEnabled(true);
            }
        });
        dataDisconnectItem.addActionListener((ActionEvent event) -> {
            if (this.currentTcpConnection != 0) {
                this.sbsDataSource.closeSource(this.currentTcpConnection);
                dataConnectItem.setEnabled(true);
                dataDisconnectItem.setEnabled(false);
                this.currentTcpConnection = 0;
            }
        });
        dataDisconnectItem.setEnabled(false);
        data.add(dataConnectItem);
        data.add(dataDisconnectItem);
        menubar.add(data);

        this.setJMenuBar(menubar);
    }

    private void openDataSource() {
        this.sbsDataSource.subscribe((SBSPacket packet) -> {
            if (packet.getHexIdent() != null) {
                if (!this.aircraftMap.containsKey(packet.getHexIdent())) {
                    this.aircraftMap.put(packet.getHexIdent(), new Aircraft(packet.getHexIdent()));
                }
                this.aircraftMap.get(packet.getHexIdent()).handleUpdate(packet);
                this.aircraftTableModel.fireTableDataChanged();
            }
        });
        this.sbsDataSource.open();
    }

    private int addTcpSource(String host, int port) {
        TCPDataSource source = new TCPDataSource(host, port);
        source.open();
        return this.sbsDataSource.addSource(source);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main1090 app = new Main1090();
            app.setVisible(true);
        });
    }
}
