package com.example.noifcalculator.expressiontree;

/**
 * Created by john.seo on 11/3/2016.
 */

public abstract class OperatorMultDivide extends Operator {
    @Override
    public void insert(ExpressionTree tree) {
        //use the lastUsedOp node to insert */ operator
        insert(tree.getLastUsedOp().getRightOperand());
    }
}
