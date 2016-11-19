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
    private String host;
    private int port;
    private Thread clientThread = null;
    private SocketClient client = null;

    public TCPDataSource(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void subscribe(DataListener listener) {
        this.subscribers.add(listener);
    }

    public void open() {
        this.client = new SocketClient(this.host, this.port);
        this.clientThread = new Thread(this.client);
        this.clientThread.start();
    }

    public void close() {
        if (this.client != null) {
            this.client.terminate();
            try {
                this.clientThread.join();
            } catch (InterruptedException ignored) {}
        }
    }

    private class SocketClient implements Runnable {
        private String host;
        private int port;
        private Socket clientSocket = null;

        public SocketClient(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public void terminate() {
            if (this.clientSocket != null) {
                try {
                    this.clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Got exception closing socket: " + e.getMessage());
                }
            }
        }

        @Override
        public void run() {
            System.out.println("Starting socket client");
            BufferedReader socketReader;
            try {
                this.clientSocket = new Socket(this.host, this.port);
            } catch (IOException e) {
                System.out.println("Could not connect to " + this.host + " on port " + this.port + ": " + e.getMessage());
                return;
            }
            try {
                socketReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
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
        }
    }
}
