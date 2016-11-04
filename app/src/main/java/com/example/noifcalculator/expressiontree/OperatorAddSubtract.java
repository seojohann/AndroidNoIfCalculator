package com.example.noifcalculator.expressiontree;

/**
 * Created by john.seo on 11/3/2016.
 */

public abstract class OperatorAddSubtract extends Operator {
    @Override
    public void insert(ExpressionTree tree) {
        //use the root node to insert +- operator
        tree.setLastUsedOp(insert(tree.getTreeRoot().getRightOperand()));

    }
}
