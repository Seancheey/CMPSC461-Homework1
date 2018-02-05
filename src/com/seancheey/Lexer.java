package com.seancheey;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 */
public class Lexer {
    private String input;
    private int searchPos = 0;
    private static final HashMap<TokenType, String> RULES = new HashMap<>();

    static {
        RULES.put(TokenType.keyword, "</?(body|b|i|ul|li)>");
        RULES.put(TokenType.string, "\\w+");
    }

    public Lexer(String input) {
        this.input = input;
    }

    public Token nextToken() {
        String subString = input.substring(searchPos);
        Matcher nonSpaceMatcher = Pattern.compile("\\S").matcher(subString);
        if (nonSpaceMatcher.find()) {
            for (TokenType type : RULES.keySet()) {
                String rule = RULES.get(type);
                Pattern p = Pattern.compile(rule);
                Matcher m = p.matcher(subString);
                if (m.find() && m.start() == nonSpaceMatcher.start()) {
                    searchPos += m.end();
                    return new Token(type, m.group());
                }
            }
        } else {
            return null;
        }
        throw new ValueException("Pattern cannot be found at pos " + searchPos + " with subString " + subString);
    }

    public ArrayList<Token> getTokenList() {
        ArrayList<Token> tokens = new ArrayList<>();
        while (true) {
            Token token = nextToken();
            if (token != null) {
                tokens.add(token);
            } else {
                break;
            }
        }
        return tokens;
    }
}