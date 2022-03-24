package com.shion1305.route.object;


import com.shion1305.route.process.WordChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessedAccessData {
    ArrayList<ArrayList<TextGroup>> pathD = new ArrayList<>();
    HashMap<ArrayList<TextGroup>, ArrayList<TextGroup>> queries = new HashMap<>();

    public ProcessedAccessData(AccessData d) {
        this(d.sourceData.url_path, d.sourceData.queries);
    }

    public ProcessedAccessData(String path, HashMap<String, String> queries) {
        String[] pathStrings = path.split("/");
        for (var p : pathStrings) {
            pathD.add(convertToTextGroup(p));
        }
        for (Map.Entry<String, String> q : queries.entrySet()) {
            this.queries.put(convertToTextGroup(q.getKey()), convertToTextGroup(q.getValue()));
        }
    }

    public static ArrayList<TextGroup> convertToTextGroup(String in) {
        ArrayList<TextGroup> result = new ArrayList<>();
        Matcher matcher = Pattern.compile("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(in);
        int pointer = 0;
        while (matcher.find()) {
            result.addAll(extractWords(in.substring(pointer, matcher.start())));
            result.add(new IPv4(new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))}));
            pointer = matcher.end();
        }
        if (pointer == 0) result.addAll(extractWords(in));
        return result;
    }

    enum ProcessMode {
        CAPTURE_MODE, NUMBER_MODE, INITIAL
    }


    public static ArrayList<TextGroup> extractWords(String in) {
        ArrayList<TextGroup> out = new ArrayList<>();
        ProcessMode status = ProcessMode.INITIAL;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < in.length(); i++) {
            ProcessMode next = isAz(in.charAt(i)) ? ProcessMode.CAPTURE_MODE : ProcessMode.NUMBER_MODE;
            if (next == status) {
                builder.append(in.charAt(i));
                continue;
            }
            switch (status) {
                case INITIAL:
                    builder.append(in.charAt(i));
                    break;
                case CAPTURE_MODE:
                    out.add(WordChecker.checkIfWord(builder.toString()));
                    builder = new StringBuilder();
                    break;
                case NUMBER_MODE:
                    out.add(new ArbitraryCharacters(builder.toString()));
                    builder = new StringBuilder();
                    break;
            }
            status = next;
        }
        switch (status) {
            case CAPTURE_MODE:
                out.add(WordChecker.checkIfWord(builder.toString()));
                break;
            case NUMBER_MODE:
                out.add(new ArbitraryCharacters(builder.toString()));
                break;
        }
        return out;
    }

    public static boolean isAz(char i) {
        //return true if i is A-Z or a-z
        return (i > 96 && i < 123) || (i > 64 && i < 91);
    }

    public void compare1(ProcessedAccessData data) {

    }
}


