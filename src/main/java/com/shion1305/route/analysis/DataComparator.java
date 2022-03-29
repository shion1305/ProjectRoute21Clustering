package com.shion1305.route.analysis;

import com.shion1305.route.object.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DataComparator {
    public enum ResultType {
        MATCH_FULL, MATCH_PATH, MATCH_NONE
    }

    public static ResultType compare1(ProcessedAccessData ac1, ProcessedAccessData ac2) {
        if (matchesPath(ac1.pathD, ac2.pathD)) {
            if (matchesQueries(ac1.queries, ac2.queries)) {
                return ResultType.MATCH_FULL;
            }
            return ResultType.MATCH_PATH;
        }
        return ResultType.MATCH_NONE;
    }

    public static boolean matchesPath(ArrayList<ArrayList<TextGroup>> tg1, ArrayList<ArrayList<TextGroup>> tg2) {
        if (tg1.size() != tg2.size()) return false;
        for (int i = 0; i < tg1.size(); i++) {
            if (!matches1(tg1.get(i), tg2.get(i))) return false;
        }
        return true;
    }

    public static boolean matchesQueries(HashMap<ArrayList<TextGroup>, ArrayList<TextGroup>> queries1, HashMap<ArrayList<TextGroup>, ArrayList<TextGroup>> queries2) {
        if (queries1.size() == 0 && queries2.size() == 0) return true;
        if (queries1.size() == 0 || queries2.size() == 0) return false;
        first_loop:
        for (var query : queries1.entrySet()) {
            for (var q : queries2.entrySet()) {
                if (matches(query.getKey(), q.getKey())) {
                    if (matches(query.getValue(), q.getValue()))
                        continue first_loop;
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean matches(ArrayList<TextGroup> tg1, ArrayList<TextGroup> tg2) {
        return matches_go(tg1, tg2) || matches_go(tg2, tg1);
    }

    public static boolean matches1(ArrayList<TextGroup> tg1, ArrayList<TextGroup> tg2) {
        return matches_go(tg1, tg2) && matches_go(tg2, tg1);
    }

    public static boolean matches_go(ArrayList<TextGroup> tg1, ArrayList<TextGroup> tg2) {
        int position = 0, groupPosition = 0, groupIndex = 0;
        int tmp = 0;
        if (tg1.size() == 0 && tg2.size() == 0) return true;
        if (tg1.size() == 0 || tg2.size() == 0) return false;
        StringBuilder bd = new StringBuilder();
        for (TextGroup tg : tg2) {
            bd.append(tg.getText());
        }
        String target = bd.toString();

        for (TextGroup tg : tg1) {
            if (tg.getClass().equals(ArbitraryCharacters.class)) {
                tmp+=tg.getText().length();
                position += tg.getText().length();
                if (position>groupPosition){
                    if (++groupIndex==tg1.size()) break;
                }
                continue;
            }
            if (tg.getClass().equals(WordGroup.class)) {
                if (tg.getText().length() + tmp > target.length()) return false;
                if (!target.startsWith(tg.getText(), tmp)) return false;
                tmp += tg.getText().length();
                continue;
            }
            if (tg.getClass().equals(IPv4.class)) {

            }
        }
        return tmp==target.length();
    }
}
