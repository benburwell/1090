package com.benburwell.planes.sbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 11/15/16.
 */
public class AggregateDataSource implements DataSource {
    private List<DataListener> subscribers = new ArrayList<>();

    public void addSource(DataSource source) {
        source.subscribe((SBSPacket packet) -> {
            for (DataListener listener : subscribers) {
                listener.handleMessage(packet);
            }
        });
    }

    public void subscribe(DataListener listener) {
        this.subscribers.add(listener);
    }
}
