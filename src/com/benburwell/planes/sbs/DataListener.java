package com.benburwell.planes.sbs;

/**
 * Created by ben on 11/15/16.
 */
public interface DataListener {
    void handleMessage(SBSPacket packet);
}
