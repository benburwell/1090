package com.benburwell.planes.sbs;

/**
 * Created by ben on 11/15/16.
 */
public enum MessageType {
    SELECTION_CHANGE("SEL"),
    NEW_ID("ID"),
    NEW_AIRCRAFT("AIR"),
    STATUS_CHANGE("STA"),
    CLICK("CLK"),
    TRANSMISSION("MSG");

    private String code;
    MessageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static MessageType parse(String messageType) throws UnrecognizedMessageTypeException {
        for (MessageType type : MessageType.values()) {
            if (type.getCode().equals(messageType)) {
                return type;
            }
        }
        throw new UnrecognizedMessageTypeException(messageType);
    }
}
