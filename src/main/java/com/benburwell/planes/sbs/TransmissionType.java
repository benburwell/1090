package com.benburwell.planes.sbs;

/**
 * @author ben
 */
public enum TransmissionType {
    ES_IDENTIFICATION(1),
    ES_SURFACE_POSITION(2),
    ES_AIRBORNE_POSITION(3),
    ES_AIRBORNE_VELOCITY(4),
    SURVEILLANCE_ALT(5),
    SURVEILLANCE_ID(6),
    AIR_TO_AIR(7),
    ALL_CALL_REPLY(8);

    private int id;
    TransmissionType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static TransmissionType parse(String codeString) throws UnrecognizedTransmissionTypeException {
        int code = Integer.parseInt(codeString);
        for (TransmissionType transmissionType : TransmissionType.values()) {
            if (transmissionType.getId() == code) {
                return transmissionType;
            }
        }
        throw new UnrecognizedTransmissionTypeException(code);
    }
}
