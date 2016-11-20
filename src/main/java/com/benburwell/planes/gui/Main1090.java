/**
 * Created by ben on 11/15/16.
 */

package com.benburwell.planes.gui;

import com.benburwell.planes.sbs.*;
import com.benburwell.planes.data.*;
import com.benburwell.planes.gui.aircraftmap.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Main1090 extends JFrame {
    private AggregateDataSource sbsDataSource = new AggregateDataSource();
    private AircraftStore aircraft = new AircraftStore();
    private NavigationAidStore navaids = new NavigationAidStore();
    private AirportStore airports = new AirportStore();
    private int currentTcpConnection = 0;
    private JTabbedPane tabbedPane = new JTabbedPane();

    public Main1090() {
        this.initUI();
    }

    private void initUI() {
        this.createMenuBar();

        this.setTitle("1090");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.openDataSource();

        try {
            this.navaids.readFromFile("/home/ben/.airdata/navaids.csv");
        } catch (IOException e) {
            System.out.println("Could not read navaid file: " + e.getMessage());
        }

        try {
            this.airports.readFromFile("/home/ben/.airdata/airports.csv");
        } catch (IOException e) {
            System.out.println("Could not read airport file: " + e.getMessage());
        }

        this.createTabs();
    }

    private void createTabs() {
        AircraftTableComponent aircraftData = new AircraftTableComponent(this.aircraft);
        this.tabbedPane.addTab("Aircraft Data", aircraftData.getComponent());

        AircraftMapComponent aircraftMap = new AircraftMapComponent(this.aircraft, this.navaids, this.airports);
        this.tabbedPane.addTab("Live Map", aircraftMap.getComponent());

        NavigationAidComponent navaids = new NavigationAidComponent(this.navaids);
        this.tabbedPane.addTab("Navigation Aids", navaids.getComponent());

        this.add(this.tabbedPane);
        this.tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        this.tabbedPane.setSelectedIndex(1);
    }

    private void createMenuBar() {
        MenuBarProvider provider = new MenuBarProvider();
        this.setJMenuBar(provider.getMenuBar());

        provider.getDataConnectItem().addActionListener((ActionEvent event) -> {
            if (this.currentTcpConnection == 0) {
                TCPConnectionOptionDialog dialog = new TCPConnectionOptionDialog();
                JOptionPane.showMessageDialog(this, dialog.getComponent(), "New Network Data Source", JOptionPane.PLAIN_MESSAGE);
                this.currentTcpConnection = this.addTcpSource(dialog.getHost(), dialog.getPort());
                provider.getDataConnectItem().setEnabled(false);
                provider.getDataDisconnectItem().setEnabled(true);
            }
        });
        provider.getDataDisconnectItem().addActionListener((ActionEvent event) -> {
            if (this.currentTcpConnection != 0) {
                this.sbsDataSource.closeSource(this.currentTcpConnection);
                provider.getDataConnectItem().setEnabled(true);
                provider.getDataDisconnectItem().setEnabled(false);
                this.currentTcpConnection = 0;
            }
        });
    }

    private void openDataSource() {
        this.sbsDataSource.subscribe((SBSPacket packet) -> {
            this.aircraft.addPacket(packet);
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
