package com.shion1305.route.analysis;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordExtractor {
    int totalCount;
    FrequencyMap frequencyMap = new FrequencyMap();

    public static void main(String[] args) throws InterruptedException {
        WordExtractor extractor = new WordExtractor();
        extractor.processData();
        export(extractor.frequencyMap);
        extractor.exportAsStream();
        System.out.println("COMPLETE");
    }


    public void processData() throws InterruptedException {
        String[] files = new File("datasets").list();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (String file : files) {
            service.submit(() -> {
                System.out.println(file);
                try {
                    List<AccessData> data = DataReader.readData(file);
                    totalCount += data.size();
                    for (AccessData d : data) {
                        if (!d.sourceData.valid) continue;
                        processString(d.sourceData.url, d);
                        for (String q : d.sourceData.queries.values()) {
                            processString(q, d);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1000000, TimeUnit.HOURS);
    }


    public static FrequencyMap export(FrequencyMap frequencyMap) {
        try (FileWriter writer = new FileWriter("words.csv")) {
            var sets = new ArrayList<>((frequencyMap).entrySet());
            var comparator = new Comparator<Map.Entry<String, Params>>() {
                @Override
                public int compare(Map.Entry<String, Params> stringIntegerEntry, Map.Entry<String, Params> t1) {
                    return t1.getValue().count.compareTo(stringIntegerEntry.getValue().count);
                }
            };
            sets.sort(comparator);
            for (var d : sets) {
                writer.write(d.getKey());
                writer.write(",");
                writer.write(String.valueOf(d.getValue().count));
                writer.write(",");
                writer.write(String.valueOf(d.getValue().ips.size()));
                writer.write(",");
                writer.write(Duration.between(d.getValue().last.toInstant(), d.getValue().first.toInstant()).toString());
                writer.write(",");
                writer.write(d.getValue().first.toString());
                writer.write(",");
                writer.write(d.getValue().last.toString());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frequencyMap;
    }

    public void exportAsStream() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("frequencyData"))) {
            oos.writeObject(frequencyMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FrequencyMap readFromPrevious() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("frequencyData"))) {
            return (FrequencyMap) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    enum ProcessStringEnum {
        INPUT_MODE, NO_MODE
    }

    public void processString(String s, AccessData tData) {
        ProcessStringEnum status = ProcessStringEnum.NO_MODE;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (isAz(s.charAt(i))) {
                builder.append(s.charAt(i));
                status = ProcessStringEnum.INPUT_MODE;
            } else if (status == ProcessStringEnum.INPUT_MODE) {
                frequencyMap.addValue(builder.toString(), tData);
                builder = new StringBuilder();
                status = ProcessStringEnum.NO_MODE;
            }
        }
        if (status == ProcessStringEnum.INPUT_MODE) {
            frequencyMap.addValue(builder.toString(), tData);
        }
    }


    public static boolean isAz(char i) {
        //return true if i is A-Z or a-z
        return (i > 96 && i < 123) || (i > 64 && i < 91);
    }
}

