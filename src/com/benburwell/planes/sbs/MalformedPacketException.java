package com.benburwell.planes.sbs;

/**
 * Created by ben on 11/15/16.
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
