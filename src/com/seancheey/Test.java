package com.seancheey;

/**
 * Created by Seancheey on 04/02/2018.
 * GitHub: https://github.com/Seancheey
 */
public class Test {
    public static void main(String[] args) {
        String t1 = "<body> google <b><i><b> yahoo</b></i></b></body>";
        String t2 = "<body> " +
                "<ul>" +
                "<li><b>asdf</b></li>" +
                "<li> google</li>" +
                "</ul> " +
                "</body>";
        new Parser(t1).run();
        new Parser(t2).run();
    }
}
