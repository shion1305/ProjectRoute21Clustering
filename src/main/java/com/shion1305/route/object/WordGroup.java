package com.shion1305.route.object;

import java.io.Serializable;

public class WordGroup implements TextGroup , Serializable {
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
