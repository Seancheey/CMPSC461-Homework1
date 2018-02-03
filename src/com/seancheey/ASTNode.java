package com.seancheey;

import java.util.ArrayList;

/**
 * Created by Seancheey on 02/02/2018.
 * GitHub: https://github.com/Seancheey
 */
public class ASTNode {
    private static final int INDENT = 2;
    private static final String INDSTRING;

    static {
        // Build the default indent string
        StringBuffer spacebuf = new StringBuffer();
        for (int i = 0; i < INDENT; i++) {
            spacebuf.append(" ");
        }
        INDSTRING = spacebuf.toString();
    }

    public String self;
    public ArrayList<ASTNode> children;

    public ASTNode(String self, ArrayList<ASTNode> children) {
        this.self = self;
        this.children = children;
    }

    public String getTreeString() {
        StringBuffer buffer = new StringBuffer(self);
        for (ASTNode node : children) {
            buffer.append(INDSTRING);
            buffer.append(node.getTreeString());
        }
        return buffer.toString();
    }
}