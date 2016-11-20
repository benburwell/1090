package com.benburwell.planes.data;

import org.apache.commons.csv.CSVRecord;

/**
 * Created by ben on 11/19/16.
 */
public abstract class AbstractCSVReader {
    public abstract void readRecord(CSVRecord record);
}
