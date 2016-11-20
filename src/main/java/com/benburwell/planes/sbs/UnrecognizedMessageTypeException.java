package com.benburwell.planes.sbs;

/**
 * @author ben
 */
public class UnrecognizedMessageTypeException extends Exception {
    private String type;

    public UnrecognizedMessageTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getMessage() {
        return "Unrecognized message type: " + this.getType();
    }
}
