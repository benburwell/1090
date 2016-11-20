/**
 * @author ben
 */

package com.benburwell.planes.gui;

import com.benburwell.planes.data.AircraftStore;
import com.benburwell.planes.data.Airport;
import com.benburwell.planes.data.CSVObjectStore;
import com.benburwell.planes.data.NavigationAid;
import com.benburwell.planes.gui.aircraftmap.AircraftMapComponent;
import com.benburwell.planes.gui.aircrafttable.AircraftTableComponent;
import com.benburwell.planes.gui.airportstable.AirportsComponent;
import com.benburwell.planes.gui.navigationaids.NavigationAidComponent;
import com.benburwell.planes.sbs.AggregateDataSource;
import com.benburwell.planes.sbs.SBSPacket;
import com.benburwell.planes.sbs.TCPDataSource;

import java.awt.EventQueue;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Main1090 extends JFrame {
    private AggregateDataSource sbsDataSource = new AggregateDataSource();
    private AircraftStore aircraft = new AircraftStore();
    private CSVObjectStore<NavigationAid> navaids = new CSVObjectStore<>();
    private CSVObjectStore<Airport> airports = new CSVObjectStore<>();
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
            this.navaids.readFromResource("/navaids.csv", NavigationAid.class);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            System.out.println("Could not read navaid file: " + e.getMessage());
        }

        try {
            this.airports.readFromResource("/airports.csv", Airport.class);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            System.out.println("Could not read airport file: " + e.getMessage());
        }

        this.createTabs();
    }

    private void createTabs() {
        List<Tabbable> tabs = new ArrayList<>();
        tabs.add(new AircraftMapComponent(this.aircraft, this.navaids, this.airports));
        tabs.add(new AircraftTableComponent(this.aircraft));
        tabs.add(new NavigationAidComponent(this.navaids));
        tabs.add(new AirportsComponent(this.airports));
        tabs.forEach(tab -> this.tabbedPane.addTab(tab.getName(), tab.getComponent()));
        this.add(this.tabbedPane);
        this.tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
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
