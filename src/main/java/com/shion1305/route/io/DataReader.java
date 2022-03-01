package com.shion1305.route.io;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shion1305.route.object.AccessData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataReader {
    public static AccessData[] readData(String fileName) throws IOException {
        try (FileInputStream stream = new FileInputStream("datasets/" + fileName)) {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(stream, AccessData[].class);
        }
    }
}
