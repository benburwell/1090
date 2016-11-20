package com.benburwell.planes.sbs;

/**
 * @author ben
 */
public class MalformedPacketException extends Exception {
    private String message;
    public MalformedPacketException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
