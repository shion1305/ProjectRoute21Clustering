package com.shion1305.route.analysis;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;

import java.io.IOException;
import java.util.*;

public class WordFrequencyCounter {

    public static FrequencyData frequencyData = new FrequencyData(1);

    public static void main(String[] args) throws IOException {
        //read data
        AccessData[] data = DataReader.readData("data2021-1210.json");
        for (AccessData d : data) {
            processString(d.sourceData.url);
            d.sourceData.headers.values().forEach(WordFrequencyCounter::processString);
        }
        System.out.println("COMPLETE");


//        HashMap<String, Integer> dFrequency3 = new HashMap<>();
//        for (int i = 0; i < 62; i++) {
//            for (int j = 0; j < 62; j++) {
//                for (int k = 0; k < 62; k++) {
//                    if (strPattern[i][j][k] != 0) {
//                        dFrequency3.put(new StringBuilder().append(toChar(i)).append(toChar(j)).append(toChar(k)).toString(), strPattern[i][j][k]);
//                    }
//                }
//            }
//        }
//        try (FileWriter writer = new FileWriter("FrequencyResult/result.csv")) {
//            for (Map.Entry<String, Integer> d : dFrequency3.entrySet()) {
//                writer.write(d.getKey());
//                writer.write(",");
//                writer.write(String.valueOf(d.getValue()));
//                writer.write("\n");
//                System.out.println(d.getKey() + "|" + d.getValue());
//            }
//        }

    }

    public static void processString(String s) {
        int status = 0;
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < FrequencyData.MAXLEVEL; i++) {
            indexes.add(62);
        }
        for (int i = 0; i < s.length(); i++) {
            int c = checkIndex(s.charAt(i));
            if (c > -1) {
                indexes.add(c);
                indexes.remove(0);
                if (status++ > 2) {
                    Integer[] aindex = new Integer[FrequencyData.MAXLEVEL];
                    aindex = indexes.toArray(aindex);
                    frequencyData.get(aindex).sum++;
                }
            } else {
                status = 0;
                indexes.clear();
                for (int j = 0; j < FrequencyData.MAXLEVEL; j++) {
                    indexes.add(62);
                }
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
    public final static int MAXLEVEL = 7;
    int level;
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
        Integer[] i1 = new Integer[i.length-1];
        System.arraycopy(i, 1, i1, 0, i.length-1);
        return child[i[0]].get(i1);
    }

    int sumChild() {
        if (level == MAXLEVEL) return sum;
        return Arrays.stream(child).parallel().reduce(new FrequencyData(level, 0), ((frequencyData, frequencyData2) -> new FrequencyData(frequencyData.level, frequencyData.sumChild() + frequencyData.sumChild()))).sum;
    }
}