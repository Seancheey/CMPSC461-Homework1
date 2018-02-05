package com.seancheey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 * Description: Lexer converts string to token list
 */
public class Lexer {
    // input of string
    private String input;
    // search starting position of lexer
    private int searchPos = 0;
    // all regex rules defined to find keyword or string
    private static final HashMap<TokenType, String> RULES = new HashMap<>();

    // static block to initialize regex of RULES
    static {
        RULES.put(TokenType.keyword, "</?(body|b|i|ul|li)>");
        RULES.put(TokenType.string, "\\w+");
    }

    public Lexer(String input) {
        this.input = input;
    }

    // designated method for Parse to find next token
    public Token nextToken() {
        String subString = input.substring(searchPos);
        Matcher nonSpaceMatcher = Pattern.compile("\\S").matcher(subString);
        // find the next non-space position as well as test if there is anything left to produce tokens
        if (nonSpaceMatcher.find()) {
            // for each rule, try parsing and return a token
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
            // no non-space character left means reaching the end. So output EOI
            return new Token(TokenType.EOI, null);
        }
        // can's find any rules to apply, output invalid
        return new Token(TokenType.invalid, null);
    }
}