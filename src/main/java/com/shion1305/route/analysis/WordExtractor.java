package com.shion1305.route.analysis;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordExtractor {

    private static final HashMap<String, Integer> frequencyMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        String[] files = new File("datasets").list();

        ExecutorService service = Executors.newFixedThreadPool(3);
        for (String file : files) {
            service.submit(() -> {
                System.out.println(file);
                try {
                    List<AccessData> data = DataReader.readData(file);
                    for (AccessData d : data) {
                        if (!d.sourceData.valid) continue;
                        processString(d.sourceData.url);
                        for (String q : d.sourceData.queries.values()) {
                            processString(q);
                        }
                        d.sourceData.headers.values().forEach(WordFrequencyCounter::processString);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1000000, TimeUnit.HOURS);
        try (FileWriter writer = new FileWriter("words.csv")) {
            var sets = new ArrayList<>((frequencyMap).entrySet());
            var comparator = new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> stringIntegerEntry, Map.Entry<String, Integer> t1) {
                    return stringIntegerEntry.getValue().compareTo(t1.getValue());
                }
            };
            sets.sort(comparator);
            for (var d : sets) {
                writer.write(d.getKey());
                writer.write(",");
                writer.write(String.valueOf(d.getValue()));
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("COMPLETE");
    }

    public static void processString(String s) {
        int status = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (isAz(s.charAt(i))) {
                builder.append(s.charAt(i));
                status = 1;
            } else if (status == 1) {
                addValue(builder.toString());
                builder = new StringBuilder();
                status = 0;
            }
        }
        if (status == 1) {
            addValue(builder.toString());
        }
    }

    private static void addValue(String key) {
        if (frequencyMap.containsKey(key)) {
            frequencyMap.put(key, frequencyMap.get(key) + 1);
        } else {
            frequencyMap.put(key, 1);
        }
    }

    public static boolean isAz(char i) {
        //return true if i is A-Z or a-z
        return (i > 96 && i < 123) || (i > 64 && i < 91);
    }
}