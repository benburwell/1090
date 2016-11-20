package com.benburwell.planes.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 11/19/16.
 */
public class CSVObjectStore<T extends AbstractCSVReader> {
    private List<T> objects = new ArrayList<>();

    public void readFromFile(String fileName, Class<T> klass) throws IOException, IllegalAccessException, InstantiationException {
        File csvData = new File(fileName);
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.RFC4180.withFirstRecordAsHeader());
        for (CSVRecord record : parser) {
            T obj = klass.newInstance();
            obj.readRecord(record);
            this.objects.add(obj);
        }
    }

    public void readFromResource(String resourceName, Class<T> klass) throws IOException, IllegalAccessException, InstantiationException {
        InputStream stream = this.getClass().getResourceAsStream(resourceName);
        Reader csvData = new BufferedReader(new InputStreamReader(stream));
        CSVParser parser = new CSVParser(csvData, CSVFormat.RFC4180.withFirstRecordAsHeader());
        for (CSVRecord record : parser) {
            T obj = klass.newInstance();
            obj.readRecord(record);
            this.objects.add(obj);
        }
    }

    public List<T> getObjects() {
        return this.objects;
    }
}
