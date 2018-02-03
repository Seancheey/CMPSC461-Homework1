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
    private String originString;
    private static final HashMap<TokenType, String> RULES = new HashMap<>();

    static {
        RULES.put(TokenType.keyword, "</?(body|b|i|ul|li)>");
        RULES.put(TokenType.string, "\\w+");
    }

    public Lexer(String originString) {
        this.originString = originString;
    }

    public ArrayList<Token> getTokenList() {
        // TODO implement
        ArrayList<Token> tokens = new ArrayList<>();
        int searchPos = 0;
        while (true) {
            String subString = originString.substring(searchPos);
            boolean found = false;
            Matcher nonSpaceMatcher = Pattern.compile("\\S").matcher(subString);
            if (nonSpaceMatcher.find()) {
                for (TokenType type : RULES.keySet()) {
                    String rule = RULES.get(type);
                    Pattern p = Pattern.compile(rule);
                    Matcher m = p.matcher(subString);
                    if (m.find() && m.start() == nonSpaceMatcher.start()) {
                        found = true;
                        tokens.add(new Token(type, m.group()));
                        searchPos += m.end();
                        break;
                    }
                }
            } else {
                break;
            }
            if (!found) {
                throw new ValueException("Pattern cannot be found at pos " + searchPos + " with subString " + subString);
            }
        }
        return tokens;
    }
}