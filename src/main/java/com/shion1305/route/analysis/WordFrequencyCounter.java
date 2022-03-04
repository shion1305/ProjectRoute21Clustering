package com.shion1305.route.analysis;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordFrequencyCounter {

    public static FrequencyData frequencyData = new FrequencyData(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        //read data
//        String[] files = new String[]{"data2021-1001.json", "data2021-1002.json", "data2021-1003.json", "data2021-1004.json", "data2021-1005.json", "data2021-1006.json", "data2021-1007.json",};
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
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1000000, TimeUnit.HOURS);
        try (FileWriter writer = new FileWriter("out")) {
            writer.write(frequencyData.export(""));
        }
        System.out.println("COMPLETE");
    }


    public static void processString(String s) {
        int status = 0;
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            int c = checkIndex(s.charAt(i));
            if (indexes.size() == FrequencyData.MAXLEVEL) {
                indexes.remove(0);
            }
            if (c > -1) {
                indexes.add(c);
                if (status > 1) {
                    if (status < FrequencyData.MAXLEVEL) status++;
                    Integer[] aindex = new Integer[status];
                    aindex = indexes.toArray(aindex);
                    frequencyData.get(aindex).ends++;
                } else {
                    status++;
                }
            } else {
                status = 0;
                indexes.clear();
            }
        }
    }

    public static int checkIndex(char i) {
        if (i > 47 && i < 58) return i - 48;
        if (i > 64 && i < 91) return i - 55;
        if (i > 96 && i < 123) return i - 61;
        return -1;
    }

    public static char toChar(int i) {
        if (i < 10) return (char) (i + 48);
        if (i < 36) return (char) (i + 55);
        if (i < 62) return (char) (i + 61);
        return 300;
    }
}

class FrequencyData {
    public final static int MAXLEVEL = 15;
    int level;
    int ends = 0;
    int sum = 0;
    FrequencyData[] child = new FrequencyData[63];

    public FrequencyData(int level) {
        this.level = level;
    }

    public FrequencyData(int level, int sum) {
        this.level = level;
        this.sum = sum;
    }

    public FrequencyData get(Integer[] i) {
        if (level == FrequencyData.MAXLEVEL + 1) return null;
        if (child[i[0]] == null) child[i[0]] = new FrequencyData(level + 1);
        if (i.length == 1) return child[i[0]];
        Integer[] i1 = new Integer[i.length - 1];
        System.arraycopy(i, 1, i1, 0, i.length - 1);
        return child[i[0]].get(i1);
    }

    int sumChild() {
        if (level == MAXLEVEL) return ends;
        return Arrays.stream(child).parallel().reduce(new FrequencyData(level, 0), ((frequencyData, frequencyData2) -> new FrequencyData(frequencyData.level, frequencyData.sumChild() + frequencyData.sumChild()))).sum + ends;
    }

    public String export(String prefix) {
        StringBuilder builder = new StringBuilder();
        if (ends != 0) builder.append(prefix).append(",").append(ends).append("\n");
        if (level != FrequencyData.MAXLEVEL) {
            for (int i = 0; i < 63; i++) {
                if (child[i] != null) {
                    StringBuilder builder1 = new StringBuilder();
                    builder1.append(prefix).append(WordFrequencyCounter.toChar(i));
                    builder.append(child[i].export(builder1.toString()));
                }
            }
        }
        return builder.toString();
    }
}