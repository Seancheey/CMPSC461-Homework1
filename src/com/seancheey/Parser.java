package com.seancheey;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 */
public class Parser {
    private String parseString;

    public Parser(String parseString) {
        this.parseString = parseString;
    }

    public void run() {
        Lexer lexer = new Lexer(parseString);
        SyntacticAnalyzer analyzer = new SyntacticAnalyzer(lexer.getTokenList());
        String treeString = analyzer.getAST().getTreeString();
        System.out.println(treeString);
    }

    public static void main(String[] args) {
        System.out.println("test");
        // new Parser(args[0]).run();
    }
}
