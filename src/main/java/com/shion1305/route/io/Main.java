package com.shion1305.route.io;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shion1305.route.object.AccessData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    static HashMap<String, AccessData> dataBySIP;

    public static void main(String[] args) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AccessData[] data = mapper.readValue(new File("datasets/data2021-1210.json"), AccessData[].class);
        System.out.println(data.length);
        System.out.println(data[0].sourceData);
        dataBySIP = new HashMap<>();

    }
}
