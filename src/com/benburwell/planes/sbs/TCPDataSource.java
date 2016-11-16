package com.benburwell.planes.sbs;

import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

/**
 * Created by ben on 11/15/16.
 */
public class TCPDataSource implements DataSource {
    private List<DataListener> subscribers = new ArrayList<>();

    public TCPDataSource(String host, int port) {
        new Thread(() -> {
            System.out.println("Starting socket client");
            Socket clientSocket;
            BufferedReader socketReader;
            try {
                clientSocket = new Socket(host, port);
            } catch (IOException e) {
                System.out.println("Could not connect to " + host + " on port " + port + ": " + e.getMessage());
                return;
            }
            try {
                socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.out.println("Could not create socket reader: " + e.getMessage());
                return;
            }

            String receivedMessage;
            while (true) {
                try {
                    receivedMessage = socketReader.readLine();
                } catch (IOException e) {
                    System.out.println("Error reading from socket: " + e.getMessage());
                    return;
                }
                try {
                    SBSPacket packet = new SBSPacket(receivedMessage);
                    for (DataListener subscriber : subscribers) {
                        subscriber.handleMessage(packet);
                    }
                } catch (MalformedPacketException e) {
                    System.out.println("Discarding malformed packet: " + receivedMessage);
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    public void subscribe(DataListener listener) {
        this.subscribers.add(listener);
    }
}
