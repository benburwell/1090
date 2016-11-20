package com.benburwell.planes.sbs;

/**
 * @author ben
 */
public interface DataListener {
    void handleMessage(SBSPacket packet);
}
