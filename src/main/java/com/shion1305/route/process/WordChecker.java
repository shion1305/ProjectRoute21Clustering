package com.shion1305.route.process;

import com.shion1305.route.analysis.FrequencyMap;
import com.shion1305.route.analysis.Params;
import com.shion1305.route.analysis.WordExtractor;
import com.shion1305.route.object.ArbitraryCharacters;
import com.shion1305.route.object.TextGroup;
import com.shion1305.route.object.WordGroup;

import java.util.HashMap;

public class WordChecker {
    static HashMap<String, Params> data = WordExtractor.readFromPrevious();

    public static TextGroup checkIfWord(String s) {
        var d = data.get(s);
        if (s.length() == 1) return new ArbitraryCharacters(s);
        if (d == null) return new ArbitraryCharacters(s);
        if (s.length() == 2 && d.ips.size() < 900) return new ArbitraryCharacters(s);
        switch (d.ips.size()) {
            case 1:
                return new ArbitraryCharacters(s);
            case 2:
                if (d.count < 11) {
                    return new WordGroup(s);
                }
            default:
                return new WordGroup(s);
        }
    }
}
