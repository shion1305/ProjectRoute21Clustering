package com.shion1305.route.object;

import java.io.Serializable;

public class WordGroup implements TextGroup, Serializable {
    public static final long serialVersionUID = 8044604689164714370L;
    public String word;
//    public long id;

    public WordGroup(String word) {
        this.word = word;
    }

    @Override
    public String getText() {
        return word;
    }
}
