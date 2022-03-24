package com.shion1305.route.object;

public class ArbitraryCharacters implements TextGroup {
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
