package com.shion1305.route.data;

import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;
import com.shion1305.route.object.ProcessedAccessData;

import java.io.*;
import java.util.ArrayList;

public class FastDataManager {
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
        System.out.println(file);
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("fastData/" + file.substring(0, file.indexOf('.'))))) {
            stream.writeObject(database);
        }
    }

    public static ArrayList<ProcessedAccessData> readDataFast(String file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("fastData/" + file))) {
            return (ArrayList<ProcessedAccessData>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
