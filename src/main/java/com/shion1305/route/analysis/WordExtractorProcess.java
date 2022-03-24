package com.shion1305.route.analysis;

import java.io.FileNotFoundException;
import java.util.Map;

public class WordExtractorProcess {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        WordExtractor extractor = new WordExtractor();
        extractor.processData();
        FrequencyMap map = extractor.frequencyMap;
        for (Map.Entry<String, Params> entry : map.entrySet()) {
            String keyword = entry.getKey();
            Params p = entry.getValue();
            if (keyword.length() <= 2 || p.ips.size() == 1) map.remove(keyword);
        }
    }
}