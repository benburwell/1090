package com.benburwell.planes.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 11/19/16.
 */
public class AirportStore {
    private List<Airport> airports = new ArrayList<>();

    public void readFromFile(String filename) throws IOException {
        File csvData = new File(filename);
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.RFC4180.withFirstRecordAsHeader());
        for (CSVRecord record : parser) {
            this.airports.add(new Airport(record));
        }
    }

    public List<Airport> getAirports() {
        return this.airports;
    }
}
