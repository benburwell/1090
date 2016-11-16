package com.benburwell.planes.sbs;

/**
 * Created by ben on 11/15/16.
 */
public class UnrecognizedTransmissionTypeException extends Exception {
    private int code;

    public UnrecognizedTransmissionTypeException(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return "Unrecognized transmission type: " + this.getCode();
    }
}
