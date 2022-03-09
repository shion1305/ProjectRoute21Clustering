package com.shion1305.route.object;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessedAccessData {
    ArrayList<TextGroup> path = new ArrayList<>();
    HashMap<ArrayList<TextGroup>, ArrayList<TextGroup>> queries = new HashMap<>();

    public ProcessedAccessData() {

    }

    public static ArrayList<TextGroup> convertToTextGroup(String in){
        return null;
    }
}

interface TextGroup {

}

class WordGroup implements TextGroup {

}

class ArbitraryCharacter implements TextGroup {

}

class SpecificFormat implements TextGroup {

}

class IPv4 extends SpecificFormat {
    int ip[] = new int[4];
    public IPv4(String ip) {
        Matcher m = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$").matcher(ip);
        if (!m.matches()) throw new IllegalArgumentException("INPUT \"" + ip + "\" is not recognized as IPv4 format");
        for (int i = 0; i < 4; i++) {
            this.ip[i]= Integer.parseInt(m.group(i+1));
        }
    }
}
