package com.shion1305.route.object;

public class ArbitraryCharacters implements TextGroup {
    public enum Type {
        NUMBER, CHARACTER, STRING
    }

    String text;
    Type type;

    public ArbitraryCharacters(String text) {
        if (text.matches("^\\d+$")) {
            this.type = Type.NUMBER;
        } else if (text.matches("^\\[A-Za-z]$")) {
            this.type = Type.CHARACTER;
        } else {
            this.type = Type.STRING;
        }
        this.text = text;
    }

    @Override
    public String toString() {
        return "ArbitraryCharacters{" +
                "text='" + text + '\'' +
                '}';
    }
}
