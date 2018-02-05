package com.seancheey;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 */
public class Parser {
    private Lexer lexer;
    private Token token;
    private static final String INDENTSTRING = "  ";

    public Parser(String input) {
        lexer = new Lexer(input);
        token = lexer.nextToken();
    }

    public void run() {
        webPageNT(0);
    }

    private void webPageNT(int indentLevel) {
        String value = verifyValue("<body>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + value);

        while (!token.getValue().equals("</body>") && token.getValue() != null) {
            textNT(indentLevel + 1);
        }

        value = verifyValue("</body>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + value);
    }

    private void textNT(int indentLevel) {
        if (token.getType() == TokenType.string) {
            String value = match(TokenType.string);
            System.out.println(indent(indentLevel) + value);
        } else if (token.getType() == TokenType.keyword) {
            String tag = match(TokenType.keyword);
            System.out.println(indent(indentLevel) + tag);
            switch (tag) {
                case "<b>":
                case "<i>":
                    textNT(indentLevel + 1);
                    break;
                case "<ul>":
                    while (token.getValue().equals("<li>")) {
                        listItemNT(indentLevel + 1);
                    }
                    break;
                default:
                    verifyValue("<b>|<i>|<ul>", tag);
            }
            tag = verifyValue(closeTag(tag), match(TokenType.keyword));
            System.out.println(indent(indentLevel) + tag);
        }
    }

    private void listItemNT(int indentLevel) {
        String tag = verifyValue("<li>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + tag);

        textNT(indentLevel + 1);

        tag = verifyValue("</li>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + tag);
    }

    private String match(TokenType type) {
        String value = token.getValue();
        if (token.getType() == type)
            token = lexer.nextToken();
        else {
            System.err.println("Syntax error: expecting: " + token.getType().toString()
                    + "; saw: "
                    + type.toString());
            System.exit(1);
        }
        return value;
    }

    private static String indent(int number) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < number; i++) {
            buffer.append(INDENTSTRING);
        }
        return buffer.toString();
    }

    private String verifyValue(String expect, String found) {
        if (!expect.equals(found)) {
            System.err.println("Expect value: " + expect + ", but found: " + found);
            System.exit(1);
            return null;
        } else {
            return expect;
        }
    }

    private String closeTag(String tag) {
        return tag.substring(0, 1) + "/" + tag.substring(1);
    }
}