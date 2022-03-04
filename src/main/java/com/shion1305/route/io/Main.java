package com.shion1305.route.io;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shion1305.route.object.AccessData;
import com.shion1305.route.process.Grouper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AccessData[] data = mapper.readValue(new File("datasets/data2021-1210.json"), AccessData[].class);
        for (AccessData d : data) {
            d.sourceData.headers.keySet().forEach(s -> System.out.println(s));
        }
        HashMap<String, ArrayList<AccessData>> d = Grouper.groupByPath(data);
        for (String p : d.keySet()) {

        }
    }
}