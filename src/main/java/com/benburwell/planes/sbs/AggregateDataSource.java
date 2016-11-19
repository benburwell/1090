package com.benburwell.planes.sbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by ben on 11/15/16.
 */
public class AggregateDataSource implements DataSource {
    private List<DataListener> subscribers = new ArrayList<>();
    private boolean isOpen = false;
    private int nextSourceNumber = 1;
    private Map<Integer,DataSource> sources = new HashMap<>();

    public int addSource(DataSource source) {
        int thisSourceNumber = this.nextSourceNumber++;
        this.sources.put(thisSourceNumber, source);
        source.subscribe((SBSPacket packet) -> {
            if (isOpen) {
                for (DataListener listener : subscribers) {
                    listener.handleMessage(packet);
                }
            }
        });
        return thisSourceNumber;
    }

    public void subscribe(DataListener listener) {
        this.subscribers.add(listener);
    }

    public void open() {
        this.isOpen = true;
    }

    public void close() {
        this.isOpen = false;
    }

    public void closeSource(int sourceNumber) {
        if (this.sources.containsKey(sourceNumber)) {
            this.sources.get(sourceNumber).close();
            this.sources.remove(sourceNumber);
        }
    }
}
