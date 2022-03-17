package com.shion1305.route.object;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessedAccessData {
    ArrayList<TextGroup> path = new ArrayList<>();
    HashMap<ArrayList<TextGroup>, ArrayList<TextGroup>> queries = new HashMap<>();

    public ProcessedAccessData(String path, HashMap<String, String> queries) {
        String[] pathStrings = path.split("/");

    }

    public static ArrayList<TextGroup> convertToTextGroup(String in) {
        ArrayList<TextGroup> result = new ArrayList<>();
        Matcher matcher = Pattern.compile("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(in);
        int pointer = 0;
        while (matcher.find()) {
            result.add(new ArbitraryCharacters(in.substring(pointer, matcher.start())));
            result.add(new IPv4(new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))}));
            pointer = matcher.end();
        }
        if (pointer == 0) result.add(new ArbitraryCharacters(in));
        return result;
    }

    public static ArrayList<TextGroup> convertToWord(){
        return null;

    }
}


interface TextGroup {

}

class WordGroup implements TextGroup {

}

class ArbitraryCharacters implements TextGroup {
    String text;

    public ArbitraryCharacters(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ArbitraryCharacters{" +
                "text='" + text + '\'' +
                '}';
    }
}

class SpecificFormat implements TextGroup {

}

class IPv4 extends SpecificFormat {
    int[] ip = new int[4];

    public IPv4(String ip) {
        Matcher m = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(ip);
        if (!m.matches()) throw new IllegalArgumentException("INPUT \"" + ip + "\" is not recognized as IPv4 format");
        for (int i = 0; i < 4; i++) {
            this.ip[i] = Integer.parseInt(m.group(i + 1));
        }
    }

    public IPv4(int[] ip) {
        if (ip.length != 4) throw new IllegalArgumentException();
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "IPv4{" +
                "ip=" + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3] +
                '}';
    }
}

enum CharacterType {
    CHARACTER, NUMBER
}
