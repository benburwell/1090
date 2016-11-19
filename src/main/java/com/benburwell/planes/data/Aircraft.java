package com.benburwell.planes.data;

import com.benburwell.planes.sbs.SBSPacket;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by ben on 11/15/16.
 */
public class Aircraft implements Comparable<Aircraft> {
    private final String hexIdent;
    private Position currentPosition = new Position();
    private List<Position> positionHistory = new ArrayList<>();
    private String callsign = "";
    private String squawk = "";
    private long packetCount = 0;
    private double track;
    private double groundSpeed;
    private double verticalRate;

    public Aircraft(String hexIdent) {
        this.hexIdent = hexIdent;
    }

    public void handleUpdate(SBSPacket packet) {
        this.packetCount++;
        if (packet.getAltitude() != null) {
            this.currentPosition.setAltitude(packet.getAltitude());
        }
        if (packet.getLatitude() != null) {
            this.currentPosition.setLatitude(packet.getLatitude());
        }
        if (packet.getLongitude() != null) {
            this.currentPosition.setLongitude(packet.getLongitude());
        }
        if (packet.getCallsign() != null && !packet.getCallsign().isEmpty()) {
            this.callsign = packet.getCallsign();
        }
        if (packet.getSquawk() != null && !packet.getSquawk().isEmpty()) {
            this.callsign = packet.getSquawk();
        }
        if (packet.getTrack() != null) {
            this.track = packet.getTrack();
        }
        if (packet.getGroundSpeed() != null) {
            this.groundSpeed = packet.getGroundSpeed();
        }
        if (packet.getVerticalRate() != null) {
            this.verticalRate = packet.getVerticalRate();
        }
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getSquawk() {
        return squawk;
    }

    public Long getPacketCount() {
        return packetCount;
    }

    public String getHexIdent() {
        return this.hexIdent;
    }

    public double getTrack() {
        return this.track;
    }

    public double getGroundSpeed() {
        return this.groundSpeed;
    }

    public double getVerticalRate() {
        return this.verticalRate;
    }

    @Override
    public int compareTo(Aircraft that) {
        return this.getHexIdent().compareTo(that.getHexIdent());
    }
}
