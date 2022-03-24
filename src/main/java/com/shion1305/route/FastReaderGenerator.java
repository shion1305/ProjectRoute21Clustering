package com.shion1305.route;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;
import com.shion1305.route.object.ProcessedAccessData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FastReaderGenerator {
    public static void main(String[] args) throws IOException {
        for (String file : new File("datasets").list()) {
            if (file.matches("^data2021-10.+$")) {
                generate(file);
            }
        }

    }

    public static void generate(String file) throws IOException {
        ArrayList<ProcessedAccessData> database = new ArrayList<>();
        var data = DataReader.readData(file);
        for (AccessData d : data) {
            ProcessedAccessData pD = new ProcessedAccessData(d);
            database.add(pD);
        }
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("fastData/" + Pattern.compile("(^.+).json$").matcher(file).toMatchResult().group(1)))) {
            stream.writeObject(database);
        }
    }
}
