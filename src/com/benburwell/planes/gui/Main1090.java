/**
 * Created by ben on 11/15/16.
 */

package com.benburwell.planes.gui;

import com.benburwell.planes.sbs.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Main1090 extends JFrame {
    private AggregateDataSource sbsDataSource = new AggregateDataSource();

    public Main1090() {
        this.initUI();
    }

    private void initUI() {
        this.createMenuBar();

        this.setTitle("1090");
        this.setSize(100, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.openDataSource();
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("1090");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem eMenuItem = new JMenuItem("Quit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit 1090");
        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        file.add(eMenuItem);
        menubar.add(file);
        this.setJMenuBar(menubar);
    }

    private void openDataSource() {
        System.out.println("asdfasdfasdfasdfasdf");
        this.sbsDataSource.addSource(new TCPDataSource("10.0.0.111", 30003));
        this.sbsDataSource.subscribe((SBSPacket packet) -> {
            System.out.println("Got message: " + packet.toString());
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main1090 app = new Main1090();
            app.setVisible(true);
        });
    }
}
