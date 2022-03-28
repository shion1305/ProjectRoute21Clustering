package com.shion1305.route.analysis;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

class WordExtractorTest {
    @Test
    void readFromPrevious() {
        String[] files = new File("datasets").list();
        WordExtractor wordExtractor = new WordExtractor();
        System.out.println(files[0]);
        try {
            List<AccessData> data = DataReader.readData(files[0]);
            for (AccessData d : data) {
                if (!d.sourceData.valid) continue;
                wordExtractor.processString(d.sourceData.url, d);
                for (String q : d.sourceData.queries.values()) {
                    wordExtractor.processString(q, d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        wordExtractor.exportAsJson();
        System.out.println(WordExtractor.readFromPrevious().size()
        );
    }
}