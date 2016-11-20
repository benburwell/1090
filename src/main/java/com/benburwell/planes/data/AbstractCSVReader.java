package com.benburwell.planes.data;

import org.apache.commons.csv.CSVRecord;

/**
 * @author ben
 */
abstract class AbstractCSVReader {
    public abstract void readRecord(CSVRecord record);
}
