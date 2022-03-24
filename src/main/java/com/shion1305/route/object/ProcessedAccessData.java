package com.shion1305.route.object;


import java.util.ArrayList;
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

    enum ProcessStringEnum {
        INPUT_MODE, NO_MODE
    }


    public static ArrayList<TextGroup> extractWords(ArbitraryCharacters group) {
        ProcessStringEnum status = ProcessStringEnum.NO_MODE;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < group.text.length(); i++) {
            if (isAz(group.text.charAt(i))) {
                builder.append(group.text.charAt(i));
                status = ProcessStringEnum.INPUT_MODE;
            } else if (status == ProcessStringEnum.INPUT_MODE) {

                builder = new StringBuilder();
                status = ProcessStringEnum.NO_MODE;
            }
        }
        if (status == ProcessStringEnum.INPUT_MODE) {

        }
        return null;
    }

    public static WordGroup isWord() {

        return null;
    }


    public static boolean isAz(char i) {
        //return true if i is A-Z or a-z
        return (i > 96 && i < 123) || (i > 64 && i < 91);
    }


    public static ArrayList<TextGroup> convertToWord() {
        return null;

    }
}


