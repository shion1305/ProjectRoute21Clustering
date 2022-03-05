package com.shion1305.route.analysis;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordExtractor {

    private static final HashMap<String, Params> frequencyMap = new HashMap<>();

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
                        processString(d.sourceData.url,d);
                        for (String q : d.sourceData.queries.values()) {
                            processString(q,d);
                        }
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
                writer.write(Duration.between(d.getValue().last.toInstant(),d.getValue().first.toInstant()).toString());
                writer.write(",");
                writer.write(d.getValue().first.toString());
                writer.write(",");
                writer.write(d.getValue().last.toString());
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("COMPLETE");
    }

    public static void processString(String s,AccessData tData) {
        int status = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (isAz(s.charAt(i))) {
                builder.append(s.charAt(i));
                status = 1;
            } else if (status == 1) {
                addValue(builder.toString(),tData);
                builder = new StringBuilder();
                status = 0;
            }
        }
        if (status == 1) {
            addValue(builder.toString(),tData);
        }
    }

    private static void addValue(String key,AccessData tData) {
        Params p;
        if (frequencyMap.containsKey(key)) {
            p = frequencyMap.get(key);
            p.count++;
            if (tData.sourceData.timestamp.before(p.first)){
                p.first=tData.sourceData.timestamp;
            }else if(tData.sourceData.timestamp.after(p.last)) {
                p.last=tData.sourceData.timestamp;
            }
            if (!p.ips.contains(tData.sourceData.source_ip)){
                p.ips.add(tData.sourceData.source_ip);
            }
        } else {
            p = new Params();
            p.ips.add(tData.sourceData.geoIp.ip);
            p.first=tData.sourceData.timestamp;
            p.last=tData.sourceData.timestamp;
        }
        frequencyMap.put(key, p);
    }

    public static boolean isAz(char i) {
        //return true if i is A-Z or a-z
        return (i > 96 && i < 123) || (i > 64 && i < 91);
    }
}
class Params{
    Integer count=1;
    ArrayList<String> ips=new ArrayList<>();
    Date first;
    Date last;
}