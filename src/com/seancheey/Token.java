package com.seancheey;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 */
public class Token {
    private TokenType type;
    private String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + type + ": " + value + '}';
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
