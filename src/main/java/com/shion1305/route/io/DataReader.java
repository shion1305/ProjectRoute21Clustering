package com.shion1305.route.io;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shion1305.route.object.AccessData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class DataReader {
    public static List<AccessData> readData(String fileName) throws IOException {
        try (FileInputStream stream = new FileInputStream("datasets/" + fileName)) {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return List.of(mapper.readValue(stream, AccessData[].class));
        }
    }
}
