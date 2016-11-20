package com.benburwell.planes.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * Created by ben on 11/19/16.
 */
public class NavigationAidStore {
    private List<NavigationAid> aids = new ArrayList<>();

    public void readFromFile(String fileName) throws IOException {
        File csvData = new File(fileName);
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.RFC4180.withFirstRecordAsHeader());
        for (CSVRecord record : parser) {
            this.aids.add(new NavigationAid(record));
        }
    }
}
