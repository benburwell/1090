package com.benburwell.planes.sbs;

/**
 * @author ben
 */
public interface DataSource {
    void subscribe(DataListener listener);
    void open();
    void close();
}
