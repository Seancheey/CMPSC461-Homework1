package com.seancheey;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 * Description: Parse the token list into AST(output in this case)
 */
public class Parser {
    // a single indent unit
    private static final String INDENTSTRING = "  ";

    // helper function to produce indent
    private static String indent(int number) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < number; i++) {
            buffer.append(INDENTSTRING);
        }
        return buffer.toString();
    }

    // helper function to produce corresponding closing tag
    private static String closeTag(String tag) {
        return tag.substring(0, 1) + "/" + tag.substring(1);
    }

    // helper function to assure tag string is same as expected
    private static String verifyValue(String expect, String found) {
        if (!expect.equals(found)) {
            System.err.println("Expect value: " + expect + ", but found: " + found);
            System.exit(1);
            return null;
        } else {
            return expect;
        }
    }

    // lexer used to produce tokens
    private Lexer lexer;
    // current token being analyzed
    private Token token;

    public Parser(String input) {
        lexer = new Lexer(input);
        token = lexer.nextToken();
    }

    // starting point of all parsing
    public void run() {
        webPageNT(0);
    }

    // parsing non-terminal "web page"
    private void webPageNT(int indentLevel) {
        // <body>
        String value = verifyValue("<body>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + value);
        // {TEXT}
        while (!token.getValue().equals("</body>") && token.getValue() != null) {
            textNT(indentLevel + 1);
        }
        // </body>
        value = verifyValue("</body>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + value);
    }

    // parsing non-terminal "text"
    private void textNT(int indentLevel) {
        if (token.getType() == TokenType.string) {
            // STRING
            String value = match(TokenType.string);
            System.out.println(indent(indentLevel) + value);
        } else if (token.getType() == TokenType.keyword) {
            // <randomTag>
            String tag = match(TokenType.keyword);
            System.out.println(indent(indentLevel) + tag);
            switch (tag) {
                case "<b>":
                case "<i>":
                    // TEXT
                    textNT(indentLevel + 1);
                    break;
                case "<ul>":
                    // {LISTITEM}
                    while (token.getValue().equals("<li>")) {
                        listItemNT(indentLevel + 1);
                    }
                    break;
                default:
                    verifyValue("<b>|<i>|<ul>", tag);
            }
            // </randomTag>
            tag = verifyValue(closeTag(tag), match(TokenType.keyword));
            System.out.println(indent(indentLevel) + tag);
        }
    }

    // parsing non-terminal "list item"
    private void listItemNT(int indentLevel) {
        // <li>
        String tag = verifyValue("<li>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + tag);
        // TEXT
        textNT(indentLevel + 1);
        // <li>
        tag = verifyValue("</li>", match(TokenType.keyword));
        System.out.println(indent(indentLevel) + tag);
    }

    // match TokenType and go to the next token
    private String match(TokenType type) {
        String value = token.getValue();
        if (token.getType() == type)
            token = lexer.nextToken();
        else {
            System.err.println("Syntax error: expecting: " + type.toString()
                    + "; saw: "
                    + token.getType().toString());
            System.exit(1);
        }
        return value;
    }
}