package com.benburwell.planes.sbs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ben
 */
public class SBSPacket {
    private MessageType messageType;
    private TransmissionType transmissionType = null;
    private String sessionId = null;
    private String aircraftId = null;
    private String hexIdent = null;
    private String flightId = null;
    private Date dateGenerated = null;
    private Date dateLogged = null;
    private String callsign = null;
    private Double altitude = null;
    private Double groundSpeed = null;
    private Double track = null;
    private Double latitude = null;
    private Double longitude = null;
    private Double verticalRate = null;
    private String squawk = null;
    private Boolean alert = null;
    private Boolean emergency = null;
    private Boolean spi = null;
    private Boolean isOnGround = null;

    public SBSPacket(String packet) throws MalformedPacketException {
        this.parse(packet);
    }

    public void parse(String packet) throws MalformedPacketException {
        String[] segments = packet.split(",", -1);
        if (segments.length < 11) {
            throw new MalformedPacketException("Packet must have at least 11 fields, but has only " + segments.length);
        }

        // get the message type
        try {
            this.messageType = MessageType.parse(segments[0]);
        } catch (UnrecognizedMessageTypeException e) {
            throw new MalformedPacketException("Packet has an unrecognized message type: " + e.getType());
        }

        // get the transmission type
        if (this.messageType.equals(MessageType.TRANSMISSION)) {
            try {
                this.transmissionType = TransmissionType.parse(segments[1]);
            } catch (UnrecognizedTransmissionTypeException e) {
                throw new MalformedPacketException("Packet has an unrecognized transmission type code: " + e.getCode());
            }
        }

        this.sessionId = segments[2];
        this.aircraftId = segments[3];
        this.hexIdent = segments[4];
        this.flightId = segments[5];
        this.dateGenerated = this.parseDateAndTime(segments[6], segments[7]);
        this.dateLogged = this.parseDateAndTime(segments[8], segments[9]);
        this.callsign = segments[10];

        if (this.messageType.equals(MessageType.TRANSMISSION)) {
            if (segments.length < 22) {
                throw new MalformedPacketException("Packet is a message (22 fields), but only has " + segments.length);
            }
            if (segments[11].length() > 0) {
                this.altitude = Double.parseDouble(segments[11]);
            }
            if (segments[12].length() > 0) {
                this.groundSpeed = Double.parseDouble(segments[12]);
            }
            if (segments[13].length() > 0) {
                this.track = Double.parseDouble(segments[13]);
            }
            if (segments[14].length() > 0) {
                this.latitude = Double.parseDouble(segments[14]);
            }
            if (segments[15].length() > 0) {
                this.longitude = Double.parseDouble(segments[15]);
            }
            if (segments[16].length() > 0) {
                this.verticalRate = Double.parseDouble(segments[16]);
            }
            this.squawk = segments[17];
            if (segments[18].length() > 0) {
                this.alert = segments[18].equals("1");
            }
            if (segments[19].length() > 0) {
                this.emergency = segments[19].equals("1");
            }
            if (segments[20].length() > 0) {
                this.spi = segments[20].equals("1");
            }
            if (segments[21].length() > 0) {
                this.isOnGround = segments[21].equals("1");
            }
        }
    }

    public Date parseDateAndTime(String date, String time) {
        String combined = "";
        Calendar now = Calendar.getInstance();
        if (date == null || date.isEmpty()) {
            combined += now.get(Calendar.YEAR) + "/" + now.get(Calendar.MONTH) + "/" + now.get(Calendar.DAY_OF_MONTH);
        } else {
            combined += date;
        }

        combined += " ";

        if (time == null || time.isEmpty()) {
            combined += now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE) + ":" +
                    now.get(Calendar.SECOND) + "." +
                    now.get(Calendar.MILLISECOND);
        } else {
            combined += time;
        }

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.sss");
        try {
            return fmt.parse(combined);
        } catch (ParseException e) {
            return null;
        }
    }

    public String toString() {
        return this.messageType.name();
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public String getHexIdent() {
        return hexIdent;
    }

    public String getFlightId() {
        return flightId;
    }

    public Date getDateGenerated() {
        return dateGenerated;
    }

    public Date getDateLogged() {
        return dateLogged;
    }

    public String getCallsign() {
        return callsign;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Double getGroundSpeed() {
        return groundSpeed;
    }

    public Double getTrack() {
        return track;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getVerticalRate() {
        return verticalRate;
    }

    public String getSquawk() {
        return squawk;
    }

    public Boolean isAlert() {
        return alert;
    }

    public Boolean isEmergency() {
        return emergency;
    }

    public Boolean isSpi() {
        return spi;
    }

    public Boolean isOnGround() {
        return isOnGround;
    }
}
