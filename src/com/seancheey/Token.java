package com.seancheey;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 */
public class Token {
    private TokenType type;
    private String string;

    public Token(TokenType type, String string) {
        this.type = type;
        this.string = string;
    }

    @Override
    public String toString() {
        return "{" + type + ": " + string + '}';
    }

    public TokenType getType() {
        return type;
    }

}
