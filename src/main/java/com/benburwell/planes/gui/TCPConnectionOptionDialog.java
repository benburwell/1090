package com.benburwell.planes.gui;

import javax.swing.*;

/**
 * Created by ben on 11/17/16.
 */
public class TCPConnectionOptionDialog implements ViewComponent {
    public static final String DEFAULT_HOSTNAME = "10.0.0.111";
    public static final int DEFAULT_TCP_PORT = 30003;

    private JPanel dialog = new JPanel();
    private JTextField hostField = new JTextField(10);
    private JTextField portField = new JTextField(5);
    private JLabel descriptionLabel = new JLabel("Add a network data source that provides data in the SBS-1 format");

    @Override
    public JComponent getComponent() {
        // set properties
        hostField.setText(DEFAULT_HOSTNAME);
        hostField.setToolTipText("Hostname or IP address");

        portField.setText(String.valueOf(DEFAULT_TCP_PORT));
        portField.setToolTipText("Port number");

        // create layout
        dialog.add(descriptionLabel);
        dialog.add(hostField);
        dialog.add(portField);

        return dialog;
    }

    public String getHost() {
        return this.hostField.getText();
    }

    public int getPort() {
        try {
            return Integer.valueOf(this.portField.getText());
        } catch (NumberFormatException e) {
            return DEFAULT_TCP_PORT;
        }
    }
}
