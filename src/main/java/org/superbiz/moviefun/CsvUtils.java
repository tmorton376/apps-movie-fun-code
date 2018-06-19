package org.superbiz.moviefun;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvUtils {

    private InputStream fileSource(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    private InputStream streamSource(String path) {
        return this.getClass().getResourceAsStream("/"+path);
    }

    private String readFile(String path) {
        InputStream source = streamSource(path);
        Scanner scanner = new Scanner(source).useDelimiter("\\A");

        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            return "";
        }
    }

    /*public static String readFile(String path) {
        try {
            Scanner scanner = new Scanner(new File(path)).useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return "";
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

    public <T> List<T> readFromCsv(ObjectReader objectReader, String path) {
        try {
            List<T> results = new ArrayList<>();

            MappingIterator<T> iterator = objectReader.readValues(readFile(path));

            while (iterator.hasNext()) {
                results.add(iterator.nextValue());
            }

            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
