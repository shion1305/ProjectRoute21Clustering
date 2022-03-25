package com.shion1305.route.analysis;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExtractRandomData {
    public static void main(String[] args) throws IOException {
        List<AccessData> data = new ArrayList<>();
        for (String file : new File("datasets").list()) {
            data.addAll(DataReader.readData(file));
        }
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()){
            String query= scanner.nextLine();
            data.forEach(accessData -> {
                if(accessData.sourceData.url.contains(query)){
                    System.out.println(accessData.sourceData.url);
                }
            });
        }

    }
}
